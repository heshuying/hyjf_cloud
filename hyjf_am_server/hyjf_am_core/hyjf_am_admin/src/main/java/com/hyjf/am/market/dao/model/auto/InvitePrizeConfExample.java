package com.hyjf.am.market.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvitePrizeConfExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public InvitePrizeConfExample() {
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

        public Criteria andPrizeNameIsNull() {
            addCriterion("prize_name is null");
            return (Criteria) this;
        }

        public Criteria andPrizeNameIsNotNull() {
            addCriterion("prize_name is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeNameEqualTo(String value) {
            addCriterion("prize_name =", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameNotEqualTo(String value) {
            addCriterion("prize_name <>", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameGreaterThan(String value) {
            addCriterion("prize_name >", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameGreaterThanOrEqualTo(String value) {
            addCriterion("prize_name >=", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameLessThan(String value) {
            addCriterion("prize_name <", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameLessThanOrEqualTo(String value) {
            addCriterion("prize_name <=", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameLike(String value) {
            addCriterion("prize_name like", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameNotLike(String value) {
            addCriterion("prize_name not like", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameIn(List<String> values) {
            addCriterion("prize_name in", values, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameNotIn(List<String> values) {
            addCriterion("prize_name not in", values, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameBetween(String value1, String value2) {
            addCriterion("prize_name between", value1, value2, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameNotBetween(String value1, String value2) {
            addCriterion("prize_name not between", value1, value2, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeQuantityIsNull() {
            addCriterion("prize_quantity is null");
            return (Criteria) this;
        }

        public Criteria andPrizeQuantityIsNotNull() {
            addCriterion("prize_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeQuantityEqualTo(Integer value) {
            addCriterion("prize_quantity =", value, "prizeQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeQuantityNotEqualTo(Integer value) {
            addCriterion("prize_quantity <>", value, "prizeQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeQuantityGreaterThan(Integer value) {
            addCriterion("prize_quantity >", value, "prizeQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_quantity >=", value, "prizeQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeQuantityLessThan(Integer value) {
            addCriterion("prize_quantity <", value, "prizeQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("prize_quantity <=", value, "prizeQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeQuantityIn(List<Integer> values) {
            addCriterion("prize_quantity in", values, "prizeQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeQuantityNotIn(List<Integer> values) {
            addCriterion("prize_quantity not in", values, "prizeQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeQuantityBetween(Integer value1, Integer value2) {
            addCriterion("prize_quantity between", value1, value2, "prizeQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_quantity not between", value1, value2, "prizeQuantity");
            return (Criteria) this;
        }

        public Criteria andRecommendQuantityIsNull() {
            addCriterion("recommend_quantity is null");
            return (Criteria) this;
        }

        public Criteria andRecommendQuantityIsNotNull() {
            addCriterion("recommend_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendQuantityEqualTo(Integer value) {
            addCriterion("recommend_quantity =", value, "recommendQuantity");
            return (Criteria) this;
        }

        public Criteria andRecommendQuantityNotEqualTo(Integer value) {
            addCriterion("recommend_quantity <>", value, "recommendQuantity");
            return (Criteria) this;
        }

        public Criteria andRecommendQuantityGreaterThan(Integer value) {
            addCriterion("recommend_quantity >", value, "recommendQuantity");
            return (Criteria) this;
        }

        public Criteria andRecommendQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("recommend_quantity >=", value, "recommendQuantity");
            return (Criteria) this;
        }

        public Criteria andRecommendQuantityLessThan(Integer value) {
            addCriterion("recommend_quantity <", value, "recommendQuantity");
            return (Criteria) this;
        }

        public Criteria andRecommendQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("recommend_quantity <=", value, "recommendQuantity");
            return (Criteria) this;
        }

        public Criteria andRecommendQuantityIn(List<Integer> values) {
            addCriterion("recommend_quantity in", values, "recommendQuantity");
            return (Criteria) this;
        }

        public Criteria andRecommendQuantityNotIn(List<Integer> values) {
            addCriterion("recommend_quantity not in", values, "recommendQuantity");
            return (Criteria) this;
        }

        public Criteria andRecommendQuantityBetween(Integer value1, Integer value2) {
            addCriterion("recommend_quantity between", value1, value2, "recommendQuantity");
            return (Criteria) this;
        }

        public Criteria andRecommendQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("recommend_quantity not between", value1, value2, "recommendQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeReminderQuantityIsNull() {
            addCriterion("prize_reminder_quantity is null");
            return (Criteria) this;
        }

        public Criteria andPrizeReminderQuantityIsNotNull() {
            addCriterion("prize_reminder_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeReminderQuantityEqualTo(Integer value) {
            addCriterion("prize_reminder_quantity =", value, "prizeReminderQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeReminderQuantityNotEqualTo(Integer value) {
            addCriterion("prize_reminder_quantity <>", value, "prizeReminderQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeReminderQuantityGreaterThan(Integer value) {
            addCriterion("prize_reminder_quantity >", value, "prizeReminderQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeReminderQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_reminder_quantity >=", value, "prizeReminderQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeReminderQuantityLessThan(Integer value) {
            addCriterion("prize_reminder_quantity <", value, "prizeReminderQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeReminderQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("prize_reminder_quantity <=", value, "prizeReminderQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeReminderQuantityIn(List<Integer> values) {
            addCriterion("prize_reminder_quantity in", values, "prizeReminderQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeReminderQuantityNotIn(List<Integer> values) {
            addCriterion("prize_reminder_quantity not in", values, "prizeReminderQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeReminderQuantityBetween(Integer value1, Integer value2) {
            addCriterion("prize_reminder_quantity between", value1, value2, "prizeReminderQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeReminderQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_reminder_quantity not between", value1, value2, "prizeReminderQuantity");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeIsNull() {
            addCriterion("prize_group_code is null");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeIsNotNull() {
            addCriterion("prize_group_code is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeEqualTo(String value) {
            addCriterion("prize_group_code =", value, "prizeGroupCode");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeNotEqualTo(String value) {
            addCriterion("prize_group_code <>", value, "prizeGroupCode");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeGreaterThan(String value) {
            addCriterion("prize_group_code >", value, "prizeGroupCode");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeGreaterThanOrEqualTo(String value) {
            addCriterion("prize_group_code >=", value, "prizeGroupCode");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeLessThan(String value) {
            addCriterion("prize_group_code <", value, "prizeGroupCode");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeLessThanOrEqualTo(String value) {
            addCriterion("prize_group_code <=", value, "prizeGroupCode");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeLike(String value) {
            addCriterion("prize_group_code like", value, "prizeGroupCode");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeNotLike(String value) {
            addCriterion("prize_group_code not like", value, "prizeGroupCode");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeIn(List<String> values) {
            addCriterion("prize_group_code in", values, "prizeGroupCode");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeNotIn(List<String> values) {
            addCriterion("prize_group_code not in", values, "prizeGroupCode");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeBetween(String value1, String value2) {
            addCriterion("prize_group_code between", value1, value2, "prizeGroupCode");
            return (Criteria) this;
        }

        public Criteria andPrizeGroupCodeNotBetween(String value1, String value2) {
            addCriterion("prize_group_code not between", value1, value2, "prizeGroupCode");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeIsNull() {
            addCriterion("prize_type is null");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeIsNotNull() {
            addCriterion("prize_type is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeEqualTo(Integer value) {
            addCriterion("prize_type =", value, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeNotEqualTo(Integer value) {
            addCriterion("prize_type <>", value, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeGreaterThan(Integer value) {
            addCriterion("prize_type >", value, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_type >=", value, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeLessThan(Integer value) {
            addCriterion("prize_type <", value, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("prize_type <=", value, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeIn(List<Integer> values) {
            addCriterion("prize_type in", values, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeNotIn(List<Integer> values) {
            addCriterion("prize_type not in", values, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeBetween(Integer value1, Integer value2) {
            addCriterion("prize_type between", value1, value2, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_type not between", value1, value2, "prizeType");
            return (Criteria) this;
        }

        public Criteria andCouponCodeIsNull() {
            addCriterion("coupon_code is null");
            return (Criteria) this;
        }

        public Criteria andCouponCodeIsNotNull() {
            addCriterion("coupon_code is not null");
            return (Criteria) this;
        }

        public Criteria andCouponCodeEqualTo(String value) {
            addCriterion("coupon_code =", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeNotEqualTo(String value) {
            addCriterion("coupon_code <>", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeGreaterThan(String value) {
            addCriterion("coupon_code >", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeGreaterThanOrEqualTo(String value) {
            addCriterion("coupon_code >=", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeLessThan(String value) {
            addCriterion("coupon_code <", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeLessThanOrEqualTo(String value) {
            addCriterion("coupon_code <=", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeLike(String value) {
            addCriterion("coupon_code like", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeNotLike(String value) {
            addCriterion("coupon_code not like", value, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeIn(List<String> values) {
            addCriterion("coupon_code in", values, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeNotIn(List<String> values) {
            addCriterion("coupon_code not in", values, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeBetween(String value1, String value2) {
            addCriterion("coupon_code between", value1, value2, "couponCode");
            return (Criteria) this;
        }

        public Criteria andCouponCodeNotBetween(String value1, String value2) {
            addCriterion("coupon_code not between", value1, value2, "couponCode");
            return (Criteria) this;
        }

        public Criteria andPrizeProbabilityIsNull() {
            addCriterion("prize_probability is null");
            return (Criteria) this;
        }

        public Criteria andPrizeProbabilityIsNotNull() {
            addCriterion("prize_probability is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeProbabilityEqualTo(BigDecimal value) {
            addCriterion("prize_probability =", value, "prizeProbability");
            return (Criteria) this;
        }

        public Criteria andPrizeProbabilityNotEqualTo(BigDecimal value) {
            addCriterion("prize_probability <>", value, "prizeProbability");
            return (Criteria) this;
        }

        public Criteria andPrizeProbabilityGreaterThan(BigDecimal value) {
            addCriterion("prize_probability >", value, "prizeProbability");
            return (Criteria) this;
        }

        public Criteria andPrizeProbabilityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_probability >=", value, "prizeProbability");
            return (Criteria) this;
        }

        public Criteria andPrizeProbabilityLessThan(BigDecimal value) {
            addCriterion("prize_probability <", value, "prizeProbability");
            return (Criteria) this;
        }

        public Criteria andPrizeProbabilityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_probability <=", value, "prizeProbability");
            return (Criteria) this;
        }

        public Criteria andPrizeProbabilityIn(List<BigDecimal> values) {
            addCriterion("prize_probability in", values, "prizeProbability");
            return (Criteria) this;
        }

        public Criteria andPrizeProbabilityNotIn(List<BigDecimal> values) {
            addCriterion("prize_probability not in", values, "prizeProbability");
            return (Criteria) this;
        }

        public Criteria andPrizeProbabilityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_probability between", value1, value2, "prizeProbability");
            return (Criteria) this;
        }

        public Criteria andPrizeProbabilityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_probability not between", value1, value2, "prizeProbability");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlIsNull() {
            addCriterion("prize_pic_url is null");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlIsNotNull() {
            addCriterion("prize_pic_url is not null");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlEqualTo(String value) {
            addCriterion("prize_pic_url =", value, "prizePicUrl");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlNotEqualTo(String value) {
            addCriterion("prize_pic_url <>", value, "prizePicUrl");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlGreaterThan(String value) {
            addCriterion("prize_pic_url >", value, "prizePicUrl");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlGreaterThanOrEqualTo(String value) {
            addCriterion("prize_pic_url >=", value, "prizePicUrl");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlLessThan(String value) {
            addCriterion("prize_pic_url <", value, "prizePicUrl");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlLessThanOrEqualTo(String value) {
            addCriterion("prize_pic_url <=", value, "prizePicUrl");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlLike(String value) {
            addCriterion("prize_pic_url like", value, "prizePicUrl");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlNotLike(String value) {
            addCriterion("prize_pic_url not like", value, "prizePicUrl");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlIn(List<String> values) {
            addCriterion("prize_pic_url in", values, "prizePicUrl");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlNotIn(List<String> values) {
            addCriterion("prize_pic_url not in", values, "prizePicUrl");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlBetween(String value1, String value2) {
            addCriterion("prize_pic_url between", value1, value2, "prizePicUrl");
            return (Criteria) this;
        }

        public Criteria andPrizePicUrlNotBetween(String value1, String value2) {
            addCriterion("prize_pic_url not between", value1, value2, "prizePicUrl");
            return (Criteria) this;
        }

        public Criteria andPrizeKindIsNull() {
            addCriterion("prize_kind is null");
            return (Criteria) this;
        }

        public Criteria andPrizeKindIsNotNull() {
            addCriterion("prize_kind is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeKindEqualTo(Integer value) {
            addCriterion("prize_kind =", value, "prizeKind");
            return (Criteria) this;
        }

        public Criteria andPrizeKindNotEqualTo(Integer value) {
            addCriterion("prize_kind <>", value, "prizeKind");
            return (Criteria) this;
        }

        public Criteria andPrizeKindGreaterThan(Integer value) {
            addCriterion("prize_kind >", value, "prizeKind");
            return (Criteria) this;
        }

        public Criteria andPrizeKindGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_kind >=", value, "prizeKind");
            return (Criteria) this;
        }

        public Criteria andPrizeKindLessThan(Integer value) {
            addCriterion("prize_kind <", value, "prizeKind");
            return (Criteria) this;
        }

        public Criteria andPrizeKindLessThanOrEqualTo(Integer value) {
            addCriterion("prize_kind <=", value, "prizeKind");
            return (Criteria) this;
        }

        public Criteria andPrizeKindIn(List<Integer> values) {
            addCriterion("prize_kind in", values, "prizeKind");
            return (Criteria) this;
        }

        public Criteria andPrizeKindNotIn(List<Integer> values) {
            addCriterion("prize_kind not in", values, "prizeKind");
            return (Criteria) this;
        }

        public Criteria andPrizeKindBetween(Integer value1, Integer value2) {
            addCriterion("prize_kind between", value1, value2, "prizeKind");
            return (Criteria) this;
        }

        public Criteria andPrizeKindNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_kind not between", value1, value2, "prizeKind");
            return (Criteria) this;
        }

        public Criteria andPrizeSortIsNull() {
            addCriterion("prize_sort is null");
            return (Criteria) this;
        }

        public Criteria andPrizeSortIsNotNull() {
            addCriterion("prize_sort is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeSortEqualTo(Integer value) {
            addCriterion("prize_sort =", value, "prizeSort");
            return (Criteria) this;
        }

        public Criteria andPrizeSortNotEqualTo(Integer value) {
            addCriterion("prize_sort <>", value, "prizeSort");
            return (Criteria) this;
        }

        public Criteria andPrizeSortGreaterThan(Integer value) {
            addCriterion("prize_sort >", value, "prizeSort");
            return (Criteria) this;
        }

        public Criteria andPrizeSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_sort >=", value, "prizeSort");
            return (Criteria) this;
        }

        public Criteria andPrizeSortLessThan(Integer value) {
            addCriterion("prize_sort <", value, "prizeSort");
            return (Criteria) this;
        }

        public Criteria andPrizeSortLessThanOrEqualTo(Integer value) {
            addCriterion("prize_sort <=", value, "prizeSort");
            return (Criteria) this;
        }

        public Criteria andPrizeSortIn(List<Integer> values) {
            addCriterion("prize_sort in", values, "prizeSort");
            return (Criteria) this;
        }

        public Criteria andPrizeSortNotIn(List<Integer> values) {
            addCriterion("prize_sort not in", values, "prizeSort");
            return (Criteria) this;
        }

        public Criteria andPrizeSortBetween(Integer value1, Integer value2) {
            addCriterion("prize_sort between", value1, value2, "prizeSort");
            return (Criteria) this;
        }

        public Criteria andPrizeSortNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_sort not between", value1, value2, "prizeSort");
            return (Criteria) this;
        }

        public Criteria andPrizeStatusIsNull() {
            addCriterion("prize_status is null");
            return (Criteria) this;
        }

        public Criteria andPrizeStatusIsNotNull() {
            addCriterion("prize_status is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeStatusEqualTo(Integer value) {
            addCriterion("prize_status =", value, "prizeStatus");
            return (Criteria) this;
        }

        public Criteria andPrizeStatusNotEqualTo(Integer value) {
            addCriterion("prize_status <>", value, "prizeStatus");
            return (Criteria) this;
        }

        public Criteria andPrizeStatusGreaterThan(Integer value) {
            addCriterion("prize_status >", value, "prizeStatus");
            return (Criteria) this;
        }

        public Criteria andPrizeStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_status >=", value, "prizeStatus");
            return (Criteria) this;
        }

        public Criteria andPrizeStatusLessThan(Integer value) {
            addCriterion("prize_status <", value, "prizeStatus");
            return (Criteria) this;
        }

        public Criteria andPrizeStatusLessThanOrEqualTo(Integer value) {
            addCriterion("prize_status <=", value, "prizeStatus");
            return (Criteria) this;
        }

        public Criteria andPrizeStatusIn(List<Integer> values) {
            addCriterion("prize_status in", values, "prizeStatus");
            return (Criteria) this;
        }

        public Criteria andPrizeStatusNotIn(List<Integer> values) {
            addCriterion("prize_status not in", values, "prizeStatus");
            return (Criteria) this;
        }

        public Criteria andPrizeStatusBetween(Integer value1, Integer value2) {
            addCriterion("prize_status between", value1, value2, "prizeStatus");
            return (Criteria) this;
        }

        public Criteria andPrizeStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_status not between", value1, value2, "prizeStatus");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageIsNull() {
            addCriterion("success_message is null");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageIsNotNull() {
            addCriterion("success_message is not null");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageEqualTo(String value) {
            addCriterion("success_message =", value, "successMessage");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageNotEqualTo(String value) {
            addCriterion("success_message <>", value, "successMessage");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageGreaterThan(String value) {
            addCriterion("success_message >", value, "successMessage");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageGreaterThanOrEqualTo(String value) {
            addCriterion("success_message >=", value, "successMessage");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageLessThan(String value) {
            addCriterion("success_message <", value, "successMessage");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageLessThanOrEqualTo(String value) {
            addCriterion("success_message <=", value, "successMessage");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageLike(String value) {
            addCriterion("success_message like", value, "successMessage");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageNotLike(String value) {
            addCriterion("success_message not like", value, "successMessage");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageIn(List<String> values) {
            addCriterion("success_message in", values, "successMessage");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageNotIn(List<String> values) {
            addCriterion("success_message not in", values, "successMessage");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageBetween(String value1, String value2) {
            addCriterion("success_message between", value1, value2, "successMessage");
            return (Criteria) this;
        }

        public Criteria andSuccessMessageNotBetween(String value1, String value2) {
            addCriterion("success_message not between", value1, value2, "successMessage");
            return (Criteria) this;
        }

        public Criteria andPrizeApplyTimeIsNull() {
            addCriterion("prize_apply_time is null");
            return (Criteria) this;
        }

        public Criteria andPrizeApplyTimeIsNotNull() {
            addCriterion("prize_apply_time is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeApplyTimeEqualTo(Integer value) {
            addCriterion("prize_apply_time =", value, "prizeApplyTime");
            return (Criteria) this;
        }

        public Criteria andPrizeApplyTimeNotEqualTo(Integer value) {
            addCriterion("prize_apply_time <>", value, "prizeApplyTime");
            return (Criteria) this;
        }

        public Criteria andPrizeApplyTimeGreaterThan(Integer value) {
            addCriterion("prize_apply_time >", value, "prizeApplyTime");
            return (Criteria) this;
        }

        public Criteria andPrizeApplyTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_apply_time >=", value, "prizeApplyTime");
            return (Criteria) this;
        }

        public Criteria andPrizeApplyTimeLessThan(Integer value) {
            addCriterion("prize_apply_time <", value, "prizeApplyTime");
            return (Criteria) this;
        }

        public Criteria andPrizeApplyTimeLessThanOrEqualTo(Integer value) {
            addCriterion("prize_apply_time <=", value, "prizeApplyTime");
            return (Criteria) this;
        }

        public Criteria andPrizeApplyTimeIn(List<Integer> values) {
            addCriterion("prize_apply_time in", values, "prizeApplyTime");
            return (Criteria) this;
        }

        public Criteria andPrizeApplyTimeNotIn(List<Integer> values) {
            addCriterion("prize_apply_time not in", values, "prizeApplyTime");
            return (Criteria) this;
        }

        public Criteria andPrizeApplyTimeBetween(Integer value1, Integer value2) {
            addCriterion("prize_apply_time between", value1, value2, "prizeApplyTime");
            return (Criteria) this;
        }

        public Criteria andPrizeApplyTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_apply_time not between", value1, value2, "prizeApplyTime");
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

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Integer value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Integer value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Integer value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Integer value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Integer value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Integer> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Integer> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Integer value1, Integer value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
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