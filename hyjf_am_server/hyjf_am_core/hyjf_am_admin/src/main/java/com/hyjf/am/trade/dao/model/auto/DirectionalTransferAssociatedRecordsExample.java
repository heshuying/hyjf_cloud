package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DirectionalTransferAssociatedRecordsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DirectionalTransferAssociatedRecordsExample() {
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

        public Criteria andTurnOutUsernameIsNull() {
            addCriterion("turn_out_username is null");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameIsNotNull() {
            addCriterion("turn_out_username is not null");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameEqualTo(String value) {
            addCriterion("turn_out_username =", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameNotEqualTo(String value) {
            addCriterion("turn_out_username <>", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameGreaterThan(String value) {
            addCriterion("turn_out_username >", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("turn_out_username >=", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameLessThan(String value) {
            addCriterion("turn_out_username <", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameLessThanOrEqualTo(String value) {
            addCriterion("turn_out_username <=", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameLike(String value) {
            addCriterion("turn_out_username like", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameNotLike(String value) {
            addCriterion("turn_out_username not like", value, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameIn(List<String> values) {
            addCriterion("turn_out_username in", values, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameNotIn(List<String> values) {
            addCriterion("turn_out_username not in", values, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameBetween(String value1, String value2) {
            addCriterion("turn_out_username between", value1, value2, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUsernameNotBetween(String value1, String value2) {
            addCriterion("turn_out_username not between", value1, value2, "turnOutUsername");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdIsNull() {
            addCriterion("turn_out_user_id is null");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdIsNotNull() {
            addCriterion("turn_out_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdEqualTo(Integer value) {
            addCriterion("turn_out_user_id =", value, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdNotEqualTo(Integer value) {
            addCriterion("turn_out_user_id <>", value, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdGreaterThan(Integer value) {
            addCriterion("turn_out_user_id >", value, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("turn_out_user_id >=", value, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdLessThan(Integer value) {
            addCriterion("turn_out_user_id <", value, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("turn_out_user_id <=", value, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdIn(List<Integer> values) {
            addCriterion("turn_out_user_id in", values, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdNotIn(List<Integer> values) {
            addCriterion("turn_out_user_id not in", values, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdBetween(Integer value1, Integer value2) {
            addCriterion("turn_out_user_id between", value1, value2, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("turn_out_user_id not between", value1, value2, "turnOutUserId");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileIsNull() {
            addCriterion("turn_out_mobile is null");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileIsNotNull() {
            addCriterion("turn_out_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileEqualTo(String value) {
            addCriterion("turn_out_mobile =", value, "turnOutMobile");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileNotEqualTo(String value) {
            addCriterion("turn_out_mobile <>", value, "turnOutMobile");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileGreaterThan(String value) {
            addCriterion("turn_out_mobile >", value, "turnOutMobile");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileGreaterThanOrEqualTo(String value) {
            addCriterion("turn_out_mobile >=", value, "turnOutMobile");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileLessThan(String value) {
            addCriterion("turn_out_mobile <", value, "turnOutMobile");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileLessThanOrEqualTo(String value) {
            addCriterion("turn_out_mobile <=", value, "turnOutMobile");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileLike(String value) {
            addCriterion("turn_out_mobile like", value, "turnOutMobile");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileNotLike(String value) {
            addCriterion("turn_out_mobile not like", value, "turnOutMobile");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileIn(List<String> values) {
            addCriterion("turn_out_mobile in", values, "turnOutMobile");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileNotIn(List<String> values) {
            addCriterion("turn_out_mobile not in", values, "turnOutMobile");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileBetween(String value1, String value2) {
            addCriterion("turn_out_mobile between", value1, value2, "turnOutMobile");
            return (Criteria) this;
        }

        public Criteria andTurnOutMobileNotBetween(String value1, String value2) {
            addCriterion("turn_out_mobile not between", value1, value2, "turnOutMobile");
            return (Criteria) this;
        }

        public Criteria andTurnOutChinapnrUsrcustidIsNull() {
            addCriterion("turn_out_chinapnr_usrcustid is null");
            return (Criteria) this;
        }

        public Criteria andTurnOutChinapnrUsrcustidIsNotNull() {
            addCriterion("turn_out_chinapnr_usrcustid is not null");
            return (Criteria) this;
        }

        public Criteria andTurnOutChinapnrUsrcustidEqualTo(Long value) {
            addCriterion("turn_out_chinapnr_usrcustid =", value, "turnOutChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andTurnOutChinapnrUsrcustidNotEqualTo(Long value) {
            addCriterion("turn_out_chinapnr_usrcustid <>", value, "turnOutChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andTurnOutChinapnrUsrcustidGreaterThan(Long value) {
            addCriterion("turn_out_chinapnr_usrcustid >", value, "turnOutChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andTurnOutChinapnrUsrcustidGreaterThanOrEqualTo(Long value) {
            addCriterion("turn_out_chinapnr_usrcustid >=", value, "turnOutChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andTurnOutChinapnrUsrcustidLessThan(Long value) {
            addCriterion("turn_out_chinapnr_usrcustid <", value, "turnOutChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andTurnOutChinapnrUsrcustidLessThanOrEqualTo(Long value) {
            addCriterion("turn_out_chinapnr_usrcustid <=", value, "turnOutChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andTurnOutChinapnrUsrcustidIn(List<Long> values) {
            addCriterion("turn_out_chinapnr_usrcustid in", values, "turnOutChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andTurnOutChinapnrUsrcustidNotIn(List<Long> values) {
            addCriterion("turn_out_chinapnr_usrcustid not in", values, "turnOutChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andTurnOutChinapnrUsrcustidBetween(Long value1, Long value2) {
            addCriterion("turn_out_chinapnr_usrcustid between", value1, value2, "turnOutChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andTurnOutChinapnrUsrcustidNotBetween(Long value1, Long value2) {
            addCriterion("turn_out_chinapnr_usrcustid not between", value1, value2, "turnOutChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameIsNull() {
            addCriterion("shift_to_username is null");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameIsNotNull() {
            addCriterion("shift_to_username is not null");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameEqualTo(String value) {
            addCriterion("shift_to_username =", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameNotEqualTo(String value) {
            addCriterion("shift_to_username <>", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameGreaterThan(String value) {
            addCriterion("shift_to_username >", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("shift_to_username >=", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameLessThan(String value) {
            addCriterion("shift_to_username <", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameLessThanOrEqualTo(String value) {
            addCriterion("shift_to_username <=", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameLike(String value) {
            addCriterion("shift_to_username like", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameNotLike(String value) {
            addCriterion("shift_to_username not like", value, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameIn(List<String> values) {
            addCriterion("shift_to_username in", values, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameNotIn(List<String> values) {
            addCriterion("shift_to_username not in", values, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameBetween(String value1, String value2) {
            addCriterion("shift_to_username between", value1, value2, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUsernameNotBetween(String value1, String value2) {
            addCriterion("shift_to_username not between", value1, value2, "shiftToUsername");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdIsNull() {
            addCriterion("shift_to_user_id is null");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdIsNotNull() {
            addCriterion("shift_to_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdEqualTo(Integer value) {
            addCriterion("shift_to_user_id =", value, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdNotEqualTo(Integer value) {
            addCriterion("shift_to_user_id <>", value, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdGreaterThan(Integer value) {
            addCriterion("shift_to_user_id >", value, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("shift_to_user_id >=", value, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdLessThan(Integer value) {
            addCriterion("shift_to_user_id <", value, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("shift_to_user_id <=", value, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdIn(List<Integer> values) {
            addCriterion("shift_to_user_id in", values, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdNotIn(List<Integer> values) {
            addCriterion("shift_to_user_id not in", values, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdBetween(Integer value1, Integer value2) {
            addCriterion("shift_to_user_id between", value1, value2, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("shift_to_user_id not between", value1, value2, "shiftToUserId");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileIsNull() {
            addCriterion("shift_to_mobile is null");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileIsNotNull() {
            addCriterion("shift_to_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileEqualTo(String value) {
            addCriterion("shift_to_mobile =", value, "shiftToMobile");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileNotEqualTo(String value) {
            addCriterion("shift_to_mobile <>", value, "shiftToMobile");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileGreaterThan(String value) {
            addCriterion("shift_to_mobile >", value, "shiftToMobile");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileGreaterThanOrEqualTo(String value) {
            addCriterion("shift_to_mobile >=", value, "shiftToMobile");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileLessThan(String value) {
            addCriterion("shift_to_mobile <", value, "shiftToMobile");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileLessThanOrEqualTo(String value) {
            addCriterion("shift_to_mobile <=", value, "shiftToMobile");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileLike(String value) {
            addCriterion("shift_to_mobile like", value, "shiftToMobile");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileNotLike(String value) {
            addCriterion("shift_to_mobile not like", value, "shiftToMobile");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileIn(List<String> values) {
            addCriterion("shift_to_mobile in", values, "shiftToMobile");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileNotIn(List<String> values) {
            addCriterion("shift_to_mobile not in", values, "shiftToMobile");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileBetween(String value1, String value2) {
            addCriterion("shift_to_mobile between", value1, value2, "shiftToMobile");
            return (Criteria) this;
        }

        public Criteria andShiftToMobileNotBetween(String value1, String value2) {
            addCriterion("shift_to_mobile not between", value1, value2, "shiftToMobile");
            return (Criteria) this;
        }

        public Criteria andShiftToChinapnrUsrcustidIsNull() {
            addCriterion("shift_to_chinapnr_usrcustid is null");
            return (Criteria) this;
        }

        public Criteria andShiftToChinapnrUsrcustidIsNotNull() {
            addCriterion("shift_to_chinapnr_usrcustid is not null");
            return (Criteria) this;
        }

        public Criteria andShiftToChinapnrUsrcustidEqualTo(Long value) {
            addCriterion("shift_to_chinapnr_usrcustid =", value, "shiftToChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andShiftToChinapnrUsrcustidNotEqualTo(Long value) {
            addCriterion("shift_to_chinapnr_usrcustid <>", value, "shiftToChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andShiftToChinapnrUsrcustidGreaterThan(Long value) {
            addCriterion("shift_to_chinapnr_usrcustid >", value, "shiftToChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andShiftToChinapnrUsrcustidGreaterThanOrEqualTo(Long value) {
            addCriterion("shift_to_chinapnr_usrcustid >=", value, "shiftToChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andShiftToChinapnrUsrcustidLessThan(Long value) {
            addCriterion("shift_to_chinapnr_usrcustid <", value, "shiftToChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andShiftToChinapnrUsrcustidLessThanOrEqualTo(Long value) {
            addCriterion("shift_to_chinapnr_usrcustid <=", value, "shiftToChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andShiftToChinapnrUsrcustidIn(List<Long> values) {
            addCriterion("shift_to_chinapnr_usrcustid in", values, "shiftToChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andShiftToChinapnrUsrcustidNotIn(List<Long> values) {
            addCriterion("shift_to_chinapnr_usrcustid not in", values, "shiftToChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andShiftToChinapnrUsrcustidBetween(Long value1, Long value2) {
            addCriterion("shift_to_chinapnr_usrcustid between", value1, value2, "shiftToChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andShiftToChinapnrUsrcustidNotBetween(Long value1, Long value2) {
            addCriterion("shift_to_chinapnr_usrcustid not between", value1, value2, "shiftToChinapnrUsrcustid");
            return (Criteria) this;
        }

        public Criteria andAssociatedStateIsNull() {
            addCriterion("associated_state is null");
            return (Criteria) this;
        }

        public Criteria andAssociatedStateIsNotNull() {
            addCriterion("associated_state is not null");
            return (Criteria) this;
        }

        public Criteria andAssociatedStateEqualTo(Integer value) {
            addCriterion("associated_state =", value, "associatedState");
            return (Criteria) this;
        }

        public Criteria andAssociatedStateNotEqualTo(Integer value) {
            addCriterion("associated_state <>", value, "associatedState");
            return (Criteria) this;
        }

        public Criteria andAssociatedStateGreaterThan(Integer value) {
            addCriterion("associated_state >", value, "associatedState");
            return (Criteria) this;
        }

        public Criteria andAssociatedStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("associated_state >=", value, "associatedState");
            return (Criteria) this;
        }

        public Criteria andAssociatedStateLessThan(Integer value) {
            addCriterion("associated_state <", value, "associatedState");
            return (Criteria) this;
        }

        public Criteria andAssociatedStateLessThanOrEqualTo(Integer value) {
            addCriterion("associated_state <=", value, "associatedState");
            return (Criteria) this;
        }

        public Criteria andAssociatedStateIn(List<Integer> values) {
            addCriterion("associated_state in", values, "associatedState");
            return (Criteria) this;
        }

        public Criteria andAssociatedStateNotIn(List<Integer> values) {
            addCriterion("associated_state not in", values, "associatedState");
            return (Criteria) this;
        }

        public Criteria andAssociatedStateBetween(Integer value1, Integer value2) {
            addCriterion("associated_state between", value1, value2, "associatedState");
            return (Criteria) this;
        }

        public Criteria andAssociatedStateNotBetween(Integer value1, Integer value2) {
            addCriterion("associated_state not between", value1, value2, "associatedState");
            return (Criteria) this;
        }

        public Criteria andAssociatedTimeIsNull() {
            addCriterion("associated_time is null");
            return (Criteria) this;
        }

        public Criteria andAssociatedTimeIsNotNull() {
            addCriterion("associated_time is not null");
            return (Criteria) this;
        }

        public Criteria andAssociatedTimeEqualTo(Date value) {
            addCriterion("associated_time =", value, "associatedTime");
            return (Criteria) this;
        }

        public Criteria andAssociatedTimeNotEqualTo(Date value) {
            addCriterion("associated_time <>", value, "associatedTime");
            return (Criteria) this;
        }

        public Criteria andAssociatedTimeGreaterThan(Date value) {
            addCriterion("associated_time >", value, "associatedTime");
            return (Criteria) this;
        }

        public Criteria andAssociatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("associated_time >=", value, "associatedTime");
            return (Criteria) this;
        }

        public Criteria andAssociatedTimeLessThan(Date value) {
            addCriterion("associated_time <", value, "associatedTime");
            return (Criteria) this;
        }

        public Criteria andAssociatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("associated_time <=", value, "associatedTime");
            return (Criteria) this;
        }

        public Criteria andAssociatedTimeIn(List<Date> values) {
            addCriterion("associated_time in", values, "associatedTime");
            return (Criteria) this;
        }

        public Criteria andAssociatedTimeNotIn(List<Date> values) {
            addCriterion("associated_time not in", values, "associatedTime");
            return (Criteria) this;
        }

        public Criteria andAssociatedTimeBetween(Date value1, Date value2) {
            addCriterion("associated_time between", value1, value2, "associatedTime");
            return (Criteria) this;
        }

        public Criteria andAssociatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("associated_time not between", value1, value2, "associatedTime");
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