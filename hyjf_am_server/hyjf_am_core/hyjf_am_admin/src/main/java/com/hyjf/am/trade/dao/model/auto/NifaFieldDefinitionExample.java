package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NifaFieldDefinitionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public NifaFieldDefinitionExample() {
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

        public Criteria andBorrowingRestrictionsIsNull() {
            addCriterion("borrowing_restrictions is null");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsIsNotNull() {
            addCriterion("borrowing_restrictions is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsEqualTo(String value) {
            addCriterion("borrowing_restrictions =", value, "borrowingRestrictions");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsNotEqualTo(String value) {
            addCriterion("borrowing_restrictions <>", value, "borrowingRestrictions");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsGreaterThan(String value) {
            addCriterion("borrowing_restrictions >", value, "borrowingRestrictions");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsGreaterThanOrEqualTo(String value) {
            addCriterion("borrowing_restrictions >=", value, "borrowingRestrictions");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsLessThan(String value) {
            addCriterion("borrowing_restrictions <", value, "borrowingRestrictions");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsLessThanOrEqualTo(String value) {
            addCriterion("borrowing_restrictions <=", value, "borrowingRestrictions");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsLike(String value) {
            addCriterion("borrowing_restrictions like", value, "borrowingRestrictions");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsNotLike(String value) {
            addCriterion("borrowing_restrictions not like", value, "borrowingRestrictions");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsIn(List<String> values) {
            addCriterion("borrowing_restrictions in", values, "borrowingRestrictions");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsNotIn(List<String> values) {
            addCriterion("borrowing_restrictions not in", values, "borrowingRestrictions");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsBetween(String value1, String value2) {
            addCriterion("borrowing_restrictions between", value1, value2, "borrowingRestrictions");
            return (Criteria) this;
        }

        public Criteria andBorrowingRestrictionsNotBetween(String value1, String value2) {
            addCriterion("borrowing_restrictions not between", value1, value2, "borrowingRestrictions");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedIsNull() {
            addCriterion("judgments_based is null");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedIsNotNull() {
            addCriterion("judgments_based is not null");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedEqualTo(String value) {
            addCriterion("judgments_based =", value, "judgmentsBased");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedNotEqualTo(String value) {
            addCriterion("judgments_based <>", value, "judgmentsBased");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedGreaterThan(String value) {
            addCriterion("judgments_based >", value, "judgmentsBased");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedGreaterThanOrEqualTo(String value) {
            addCriterion("judgments_based >=", value, "judgmentsBased");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedLessThan(String value) {
            addCriterion("judgments_based <", value, "judgmentsBased");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedLessThanOrEqualTo(String value) {
            addCriterion("judgments_based <=", value, "judgmentsBased");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedLike(String value) {
            addCriterion("judgments_based like", value, "judgmentsBased");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedNotLike(String value) {
            addCriterion("judgments_based not like", value, "judgmentsBased");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedIn(List<String> values) {
            addCriterion("judgments_based in", values, "judgmentsBased");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedNotIn(List<String> values) {
            addCriterion("judgments_based not in", values, "judgmentsBased");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedBetween(String value1, String value2) {
            addCriterion("judgments_based between", value1, value2, "judgmentsBased");
            return (Criteria) this;
        }

        public Criteria andJudgmentsBasedNotBetween(String value1, String value2) {
            addCriterion("judgments_based not between", value1, value2, "judgmentsBased");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleIsNull() {
            addCriterion("repay_date_rule is null");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleIsNotNull() {
            addCriterion("repay_date_rule is not null");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleEqualTo(String value) {
            addCriterion("repay_date_rule =", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleNotEqualTo(String value) {
            addCriterion("repay_date_rule <>", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleGreaterThan(String value) {
            addCriterion("repay_date_rule >", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleGreaterThanOrEqualTo(String value) {
            addCriterion("repay_date_rule >=", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleLessThan(String value) {
            addCriterion("repay_date_rule <", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleLessThanOrEqualTo(String value) {
            addCriterion("repay_date_rule <=", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleLike(String value) {
            addCriterion("repay_date_rule like", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleNotLike(String value) {
            addCriterion("repay_date_rule not like", value, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleIn(List<String> values) {
            addCriterion("repay_date_rule in", values, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleNotIn(List<String> values) {
            addCriterion("repay_date_rule not in", values, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleBetween(String value1, String value2) {
            addCriterion("repay_date_rule between", value1, value2, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andRepayDateRuleNotBetween(String value1, String value2) {
            addCriterion("repay_date_rule not between", value1, value2, "repayDateRule");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionIsNull() {
            addCriterion("overdue_definition is null");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionIsNotNull() {
            addCriterion("overdue_definition is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionEqualTo(String value) {
            addCriterion("overdue_definition =", value, "overdueDefinition");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionNotEqualTo(String value) {
            addCriterion("overdue_definition <>", value, "overdueDefinition");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionGreaterThan(String value) {
            addCriterion("overdue_definition >", value, "overdueDefinition");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_definition >=", value, "overdueDefinition");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionLessThan(String value) {
            addCriterion("overdue_definition <", value, "overdueDefinition");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionLessThanOrEqualTo(String value) {
            addCriterion("overdue_definition <=", value, "overdueDefinition");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionLike(String value) {
            addCriterion("overdue_definition like", value, "overdueDefinition");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionNotLike(String value) {
            addCriterion("overdue_definition not like", value, "overdueDefinition");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionIn(List<String> values) {
            addCriterion("overdue_definition in", values, "overdueDefinition");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionNotIn(List<String> values) {
            addCriterion("overdue_definition not in", values, "overdueDefinition");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionBetween(String value1, String value2) {
            addCriterion("overdue_definition between", value1, value2, "overdueDefinition");
            return (Criteria) this;
        }

        public Criteria andOverdueDefinitionNotBetween(String value1, String value2) {
            addCriterion("overdue_definition not between", value1, value2, "overdueDefinition");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityIsNull() {
            addCriterion("overdue_responsibility is null");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityIsNotNull() {
            addCriterion("overdue_responsibility is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityEqualTo(String value) {
            addCriterion("overdue_responsibility =", value, "overdueResponsibility");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityNotEqualTo(String value) {
            addCriterion("overdue_responsibility <>", value, "overdueResponsibility");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityGreaterThan(String value) {
            addCriterion("overdue_responsibility >", value, "overdueResponsibility");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_responsibility >=", value, "overdueResponsibility");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityLessThan(String value) {
            addCriterion("overdue_responsibility <", value, "overdueResponsibility");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityLessThanOrEqualTo(String value) {
            addCriterion("overdue_responsibility <=", value, "overdueResponsibility");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityLike(String value) {
            addCriterion("overdue_responsibility like", value, "overdueResponsibility");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityNotLike(String value) {
            addCriterion("overdue_responsibility not like", value, "overdueResponsibility");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityIn(List<String> values) {
            addCriterion("overdue_responsibility in", values, "overdueResponsibility");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityNotIn(List<String> values) {
            addCriterion("overdue_responsibility not in", values, "overdueResponsibility");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityBetween(String value1, String value2) {
            addCriterion("overdue_responsibility between", value1, value2, "overdueResponsibility");
            return (Criteria) this;
        }

        public Criteria andOverdueResponsibilityNotBetween(String value1, String value2) {
            addCriterion("overdue_responsibility not between", value1, value2, "overdueResponsibility");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessIsNull() {
            addCriterion("overdue_process is null");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessIsNotNull() {
            addCriterion("overdue_process is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessEqualTo(String value) {
            addCriterion("overdue_process =", value, "overdueProcess");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessNotEqualTo(String value) {
            addCriterion("overdue_process <>", value, "overdueProcess");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessGreaterThan(String value) {
            addCriterion("overdue_process >", value, "overdueProcess");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_process >=", value, "overdueProcess");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessLessThan(String value) {
            addCriterion("overdue_process <", value, "overdueProcess");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessLessThanOrEqualTo(String value) {
            addCriterion("overdue_process <=", value, "overdueProcess");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessLike(String value) {
            addCriterion("overdue_process like", value, "overdueProcess");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessNotLike(String value) {
            addCriterion("overdue_process not like", value, "overdueProcess");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessIn(List<String> values) {
            addCriterion("overdue_process in", values, "overdueProcess");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessNotIn(List<String> values) {
            addCriterion("overdue_process not in", values, "overdueProcess");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessBetween(String value1, String value2) {
            addCriterion("overdue_process between", value1, value2, "overdueProcess");
            return (Criteria) this;
        }

        public Criteria andOverdueProcessNotBetween(String value1, String value2) {
            addCriterion("overdue_process not between", value1, value2, "overdueProcess");
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