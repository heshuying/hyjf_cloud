package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public ProductUserExample() {
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

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(BigDecimal value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(BigDecimal value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(BigDecimal value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(BigDecimal value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<BigDecimal> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<BigDecimal> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total not between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andValidDaysIsNull() {
            addCriterion("valid_days is null");
            return (Criteria) this;
        }

        public Criteria andValidDaysIsNotNull() {
            addCriterion("valid_days is not null");
            return (Criteria) this;
        }

        public Criteria andValidDaysEqualTo(Integer value) {
            addCriterion("valid_days =", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysNotEqualTo(Integer value) {
            addCriterion("valid_days <>", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysGreaterThan(Integer value) {
            addCriterion("valid_days >", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysGreaterThanOrEqualTo(Integer value) {
            addCriterion("valid_days >=", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysLessThan(Integer value) {
            addCriterion("valid_days <", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysLessThanOrEqualTo(Integer value) {
            addCriterion("valid_days <=", value, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysIn(List<Integer> values) {
            addCriterion("valid_days in", values, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysNotIn(List<Integer> values) {
            addCriterion("valid_days not in", values, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysBetween(Integer value1, Integer value2) {
            addCriterion("valid_days between", value1, value2, "validDays");
            return (Criteria) this;
        }

        public Criteria andValidDaysNotBetween(Integer value1, Integer value2) {
            addCriterion("valid_days not between", value1, value2, "validDays");
            return (Criteria) this;
        }

        public Criteria andRedeemedIsNull() {
            addCriterion("redeemed is null");
            return (Criteria) this;
        }

        public Criteria andRedeemedIsNotNull() {
            addCriterion("redeemed is not null");
            return (Criteria) this;
        }

        public Criteria andRedeemedEqualTo(BigDecimal value) {
            addCriterion("redeemed =", value, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedNotEqualTo(BigDecimal value) {
            addCriterion("redeemed <>", value, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedGreaterThan(BigDecimal value) {
            addCriterion("redeemed >", value, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("redeemed >=", value, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedLessThan(BigDecimal value) {
            addCriterion("redeemed <", value, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedLessThanOrEqualTo(BigDecimal value) {
            addCriterion("redeemed <=", value, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedIn(List<BigDecimal> values) {
            addCriterion("redeemed in", values, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedNotIn(List<BigDecimal> values) {
            addCriterion("redeemed not in", values, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("redeemed between", value1, value2, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRedeemedNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("redeemed not between", value1, value2, "redeemed");
            return (Criteria) this;
        }

        public Criteria andRestAmountIsNull() {
            addCriterion("rest_amount is null");
            return (Criteria) this;
        }

        public Criteria andRestAmountIsNotNull() {
            addCriterion("rest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRestAmountEqualTo(BigDecimal value) {
            addCriterion("rest_amount =", value, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountNotEqualTo(BigDecimal value) {
            addCriterion("rest_amount <>", value, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountGreaterThan(BigDecimal value) {
            addCriterion("rest_amount >", value, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("rest_amount >=", value, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountLessThan(BigDecimal value) {
            addCriterion("rest_amount <", value, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("rest_amount <=", value, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountIn(List<BigDecimal> values) {
            addCriterion("rest_amount in", values, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountNotIn(List<BigDecimal> values) {
            addCriterion("rest_amount not in", values, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rest_amount between", value1, value2, "restAmount");
            return (Criteria) this;
        }

        public Criteria andRestAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rest_amount not between", value1, value2, "restAmount");
            return (Criteria) this;
        }

        public Criteria andInterestIsNull() {
            addCriterion("interest is null");
            return (Criteria) this;
        }

        public Criteria andInterestIsNotNull() {
            addCriterion("interest is not null");
            return (Criteria) this;
        }

        public Criteria andInterestEqualTo(BigDecimal value) {
            addCriterion("interest =", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotEqualTo(BigDecimal value) {
            addCriterion("interest <>", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThan(BigDecimal value) {
            addCriterion("interest >", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("interest >=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThan(BigDecimal value) {
            addCriterion("interest <", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("interest <=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestIn(List<BigDecimal> values) {
            addCriterion("interest in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotIn(List<BigDecimal> values) {
            addCriterion("interest not in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest not between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andRestAmountCheckIsNull() {
            addCriterion("rest_amount_check is null");
            return (Criteria) this;
        }

        public Criteria andRestAmountCheckIsNotNull() {
            addCriterion("rest_amount_check is not null");
            return (Criteria) this;
        }

        public Criteria andRestAmountCheckEqualTo(BigDecimal value) {
            addCriterion("rest_amount_check =", value, "restAmountCheck");
            return (Criteria) this;
        }

        public Criteria andRestAmountCheckNotEqualTo(BigDecimal value) {
            addCriterion("rest_amount_check <>", value, "restAmountCheck");
            return (Criteria) this;
        }

        public Criteria andRestAmountCheckGreaterThan(BigDecimal value) {
            addCriterion("rest_amount_check >", value, "restAmountCheck");
            return (Criteria) this;
        }

        public Criteria andRestAmountCheckGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("rest_amount_check >=", value, "restAmountCheck");
            return (Criteria) this;
        }

        public Criteria andRestAmountCheckLessThan(BigDecimal value) {
            addCriterion("rest_amount_check <", value, "restAmountCheck");
            return (Criteria) this;
        }

        public Criteria andRestAmountCheckLessThanOrEqualTo(BigDecimal value) {
            addCriterion("rest_amount_check <=", value, "restAmountCheck");
            return (Criteria) this;
        }

        public Criteria andRestAmountCheckIn(List<BigDecimal> values) {
            addCriterion("rest_amount_check in", values, "restAmountCheck");
            return (Criteria) this;
        }

        public Criteria andRestAmountCheckNotIn(List<BigDecimal> values) {
            addCriterion("rest_amount_check not in", values, "restAmountCheck");
            return (Criteria) this;
        }

        public Criteria andRestAmountCheckBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rest_amount_check between", value1, value2, "restAmountCheck");
            return (Criteria) this;
        }

        public Criteria andRestAmountCheckNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rest_amount_check not between", value1, value2, "restAmountCheck");
            return (Criteria) this;
        }

        public Criteria andRedeemedCheckIsNull() {
            addCriterion("redeemed_check is null");
            return (Criteria) this;
        }

        public Criteria andRedeemedCheckIsNotNull() {
            addCriterion("redeemed_check is not null");
            return (Criteria) this;
        }

        public Criteria andRedeemedCheckEqualTo(BigDecimal value) {
            addCriterion("redeemed_check =", value, "redeemedCheck");
            return (Criteria) this;
        }

        public Criteria andRedeemedCheckNotEqualTo(BigDecimal value) {
            addCriterion("redeemed_check <>", value, "redeemedCheck");
            return (Criteria) this;
        }

        public Criteria andRedeemedCheckGreaterThan(BigDecimal value) {
            addCriterion("redeemed_check >", value, "redeemedCheck");
            return (Criteria) this;
        }

        public Criteria andRedeemedCheckGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("redeemed_check >=", value, "redeemedCheck");
            return (Criteria) this;
        }

        public Criteria andRedeemedCheckLessThan(BigDecimal value) {
            addCriterion("redeemed_check <", value, "redeemedCheck");
            return (Criteria) this;
        }

        public Criteria andRedeemedCheckLessThanOrEqualTo(BigDecimal value) {
            addCriterion("redeemed_check <=", value, "redeemedCheck");
            return (Criteria) this;
        }

        public Criteria andRedeemedCheckIn(List<BigDecimal> values) {
            addCriterion("redeemed_check in", values, "redeemedCheck");
            return (Criteria) this;
        }

        public Criteria andRedeemedCheckNotIn(List<BigDecimal> values) {
            addCriterion("redeemed_check not in", values, "redeemedCheck");
            return (Criteria) this;
        }

        public Criteria andRedeemedCheckBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("redeemed_check between", value1, value2, "redeemedCheck");
            return (Criteria) this;
        }

        public Criteria andRedeemedCheckNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("redeemed_check not between", value1, value2, "redeemedCheck");
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