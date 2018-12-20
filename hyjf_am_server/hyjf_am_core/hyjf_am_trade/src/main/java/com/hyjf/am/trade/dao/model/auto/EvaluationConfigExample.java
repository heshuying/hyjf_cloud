package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EvaluationConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public EvaluationConfigExample() {
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

        public Criteria andDebtEvaluationTypeCheckIsNull() {
            addCriterion("debt_evaluation_type_check is null");
            return (Criteria) this;
        }

        public Criteria andDebtEvaluationTypeCheckIsNotNull() {
            addCriterion("debt_evaluation_type_check is not null");
            return (Criteria) this;
        }

        public Criteria andDebtEvaluationTypeCheckEqualTo(Boolean value) {
            addCriterion("debt_evaluation_type_check =", value, "debtEvaluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andDebtEvaluationTypeCheckNotEqualTo(Boolean value) {
            addCriterion("debt_evaluation_type_check <>", value, "debtEvaluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andDebtEvaluationTypeCheckGreaterThan(Boolean value) {
            addCriterion("debt_evaluation_type_check >", value, "debtEvaluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andDebtEvaluationTypeCheckGreaterThanOrEqualTo(Boolean value) {
            addCriterion("debt_evaluation_type_check >=", value, "debtEvaluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andDebtEvaluationTypeCheckLessThan(Boolean value) {
            addCriterion("debt_evaluation_type_check <", value, "debtEvaluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andDebtEvaluationTypeCheckLessThanOrEqualTo(Boolean value) {
            addCriterion("debt_evaluation_type_check <=", value, "debtEvaluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andDebtEvaluationTypeCheckIn(List<Boolean> values) {
            addCriterion("debt_evaluation_type_check in", values, "debtEvaluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andDebtEvaluationTypeCheckNotIn(List<Boolean> values) {
            addCriterion("debt_evaluation_type_check not in", values, "debtEvaluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andDebtEvaluationTypeCheckBetween(Boolean value1, Boolean value2) {
            addCriterion("debt_evaluation_type_check between", value1, value2, "debtEvaluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andDebtEvaluationTypeCheckNotBetween(Boolean value1, Boolean value2) {
            addCriterion("debt_evaluation_type_check not between", value1, value2, "debtEvaluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEveluationTypeCheckIsNull() {
            addCriterion("intellectual_eveluation_type_check is null");
            return (Criteria) this;
        }

        public Criteria andIntellectualEveluationTypeCheckIsNotNull() {
            addCriterion("intellectual_eveluation_type_check is not null");
            return (Criteria) this;
        }

        public Criteria andIntellectualEveluationTypeCheckEqualTo(Boolean value) {
            addCriterion("intellectual_eveluation_type_check =", value, "intellectualEveluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEveluationTypeCheckNotEqualTo(Boolean value) {
            addCriterion("intellectual_eveluation_type_check <>", value, "intellectualEveluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEveluationTypeCheckGreaterThan(Boolean value) {
            addCriterion("intellectual_eveluation_type_check >", value, "intellectualEveluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEveluationTypeCheckGreaterThanOrEqualTo(Boolean value) {
            addCriterion("intellectual_eveluation_type_check >=", value, "intellectualEveluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEveluationTypeCheckLessThan(Boolean value) {
            addCriterion("intellectual_eveluation_type_check <", value, "intellectualEveluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEveluationTypeCheckLessThanOrEqualTo(Boolean value) {
            addCriterion("intellectual_eveluation_type_check <=", value, "intellectualEveluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEveluationTypeCheckIn(List<Boolean> values) {
            addCriterion("intellectual_eveluation_type_check in", values, "intellectualEveluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEveluationTypeCheckNotIn(List<Boolean> values) {
            addCriterion("intellectual_eveluation_type_check not in", values, "intellectualEveluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEveluationTypeCheckBetween(Boolean value1, Boolean value2) {
            addCriterion("intellectual_eveluation_type_check between", value1, value2, "intellectualEveluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEveluationTypeCheckNotBetween(Boolean value1, Boolean value2) {
            addCriterion("intellectual_eveluation_type_check not between", value1, value2, "intellectualEveluationTypeCheck");
            return (Criteria) this;
        }

        public Criteria andDeptEvaluationMoneyCheckIsNull() {
            addCriterion("dept_evaluation_money_check is null");
            return (Criteria) this;
        }

        public Criteria andDeptEvaluationMoneyCheckIsNotNull() {
            addCriterion("dept_evaluation_money_check is not null");
            return (Criteria) this;
        }

        public Criteria andDeptEvaluationMoneyCheckEqualTo(Boolean value) {
            addCriterion("dept_evaluation_money_check =", value, "deptEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andDeptEvaluationMoneyCheckNotEqualTo(Boolean value) {
            addCriterion("dept_evaluation_money_check <>", value, "deptEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andDeptEvaluationMoneyCheckGreaterThan(Boolean value) {
            addCriterion("dept_evaluation_money_check >", value, "deptEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andDeptEvaluationMoneyCheckGreaterThanOrEqualTo(Boolean value) {
            addCriterion("dept_evaluation_money_check >=", value, "deptEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andDeptEvaluationMoneyCheckLessThan(Boolean value) {
            addCriterion("dept_evaluation_money_check <", value, "deptEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andDeptEvaluationMoneyCheckLessThanOrEqualTo(Boolean value) {
            addCriterion("dept_evaluation_money_check <=", value, "deptEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andDeptEvaluationMoneyCheckIn(List<Boolean> values) {
            addCriterion("dept_evaluation_money_check in", values, "deptEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andDeptEvaluationMoneyCheckNotIn(List<Boolean> values) {
            addCriterion("dept_evaluation_money_check not in", values, "deptEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andDeptEvaluationMoneyCheckBetween(Boolean value1, Boolean value2) {
            addCriterion("dept_evaluation_money_check between", value1, value2, "deptEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andDeptEvaluationMoneyCheckNotBetween(Boolean value1, Boolean value2) {
            addCriterion("dept_evaluation_money_check not between", value1, value2, "deptEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEvaluationMoneyCheckIsNull() {
            addCriterion("intellectual_evaluation_money_check is null");
            return (Criteria) this;
        }

        public Criteria andIntellectualEvaluationMoneyCheckIsNotNull() {
            addCriterion("intellectual_evaluation_money_check is not null");
            return (Criteria) this;
        }

        public Criteria andIntellectualEvaluationMoneyCheckEqualTo(Boolean value) {
            addCriterion("intellectual_evaluation_money_check =", value, "intellectualEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEvaluationMoneyCheckNotEqualTo(Boolean value) {
            addCriterion("intellectual_evaluation_money_check <>", value, "intellectualEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEvaluationMoneyCheckGreaterThan(Boolean value) {
            addCriterion("intellectual_evaluation_money_check >", value, "intellectualEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEvaluationMoneyCheckGreaterThanOrEqualTo(Boolean value) {
            addCriterion("intellectual_evaluation_money_check >=", value, "intellectualEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEvaluationMoneyCheckLessThan(Boolean value) {
            addCriterion("intellectual_evaluation_money_check <", value, "intellectualEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEvaluationMoneyCheckLessThanOrEqualTo(Boolean value) {
            addCriterion("intellectual_evaluation_money_check <=", value, "intellectualEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEvaluationMoneyCheckIn(List<Boolean> values) {
            addCriterion("intellectual_evaluation_money_check in", values, "intellectualEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEvaluationMoneyCheckNotIn(List<Boolean> values) {
            addCriterion("intellectual_evaluation_money_check not in", values, "intellectualEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEvaluationMoneyCheckBetween(Boolean value1, Boolean value2) {
            addCriterion("intellectual_evaluation_money_check between", value1, value2, "intellectualEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualEvaluationMoneyCheckNotBetween(Boolean value1, Boolean value2) {
            addCriterion("intellectual_evaluation_money_check not between", value1, value2, "intellectualEvaluationMoneyCheck");
            return (Criteria) this;
        }

        public Criteria andDeptCollectionEvaluationCheckIsNull() {
            addCriterion("dept_collection_evaluation_check is null");
            return (Criteria) this;
        }

        public Criteria andDeptCollectionEvaluationCheckIsNotNull() {
            addCriterion("dept_collection_evaluation_check is not null");
            return (Criteria) this;
        }

        public Criteria andDeptCollectionEvaluationCheckEqualTo(Boolean value) {
            addCriterion("dept_collection_evaluation_check =", value, "deptCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andDeptCollectionEvaluationCheckNotEqualTo(Boolean value) {
            addCriterion("dept_collection_evaluation_check <>", value, "deptCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andDeptCollectionEvaluationCheckGreaterThan(Boolean value) {
            addCriterion("dept_collection_evaluation_check >", value, "deptCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andDeptCollectionEvaluationCheckGreaterThanOrEqualTo(Boolean value) {
            addCriterion("dept_collection_evaluation_check >=", value, "deptCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andDeptCollectionEvaluationCheckLessThan(Boolean value) {
            addCriterion("dept_collection_evaluation_check <", value, "deptCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andDeptCollectionEvaluationCheckLessThanOrEqualTo(Boolean value) {
            addCriterion("dept_collection_evaluation_check <=", value, "deptCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andDeptCollectionEvaluationCheckIn(List<Boolean> values) {
            addCriterion("dept_collection_evaluation_check in", values, "deptCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andDeptCollectionEvaluationCheckNotIn(List<Boolean> values) {
            addCriterion("dept_collection_evaluation_check not in", values, "deptCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andDeptCollectionEvaluationCheckBetween(Boolean value1, Boolean value2) {
            addCriterion("dept_collection_evaluation_check between", value1, value2, "deptCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andDeptCollectionEvaluationCheckNotBetween(Boolean value1, Boolean value2) {
            addCriterion("dept_collection_evaluation_check not between", value1, value2, "deptCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualCollectionEvaluationCheckIsNull() {
            addCriterion("intellectual_collection_evaluation_check is null");
            return (Criteria) this;
        }

        public Criteria andIntellectualCollectionEvaluationCheckIsNotNull() {
            addCriterion("intellectual_collection_evaluation_check is not null");
            return (Criteria) this;
        }

        public Criteria andIntellectualCollectionEvaluationCheckEqualTo(Boolean value) {
            addCriterion("intellectual_collection_evaluation_check =", value, "intellectualCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualCollectionEvaluationCheckNotEqualTo(Boolean value) {
            addCriterion("intellectual_collection_evaluation_check <>", value, "intellectualCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualCollectionEvaluationCheckGreaterThan(Boolean value) {
            addCriterion("intellectual_collection_evaluation_check >", value, "intellectualCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualCollectionEvaluationCheckGreaterThanOrEqualTo(Boolean value) {
            addCriterion("intellectual_collection_evaluation_check >=", value, "intellectualCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualCollectionEvaluationCheckLessThan(Boolean value) {
            addCriterion("intellectual_collection_evaluation_check <", value, "intellectualCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualCollectionEvaluationCheckLessThanOrEqualTo(Boolean value) {
            addCriterion("intellectual_collection_evaluation_check <=", value, "intellectualCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualCollectionEvaluationCheckIn(List<Boolean> values) {
            addCriterion("intellectual_collection_evaluation_check in", values, "intellectualCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualCollectionEvaluationCheckNotIn(List<Boolean> values) {
            addCriterion("intellectual_collection_evaluation_check not in", values, "intellectualCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualCollectionEvaluationCheckBetween(Boolean value1, Boolean value2) {
            addCriterion("intellectual_collection_evaluation_check between", value1, value2, "intellectualCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andIntellectualCollectionEvaluationCheckNotBetween(Boolean value1, Boolean value2) {
            addCriterion("intellectual_collection_evaluation_check not between", value1, value2, "intellectualCollectionEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andInvestmentEvaluationCheckIsNull() {
            addCriterion("investment_evaluation_check is null");
            return (Criteria) this;
        }

        public Criteria andInvestmentEvaluationCheckIsNotNull() {
            addCriterion("investment_evaluation_check is not null");
            return (Criteria) this;
        }

        public Criteria andInvestmentEvaluationCheckEqualTo(Boolean value) {
            addCriterion("investment_evaluation_check =", value, "investmentEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andInvestmentEvaluationCheckNotEqualTo(Boolean value) {
            addCriterion("investment_evaluation_check <>", value, "investmentEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andInvestmentEvaluationCheckGreaterThan(Boolean value) {
            addCriterion("investment_evaluation_check >", value, "investmentEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andInvestmentEvaluationCheckGreaterThanOrEqualTo(Boolean value) {
            addCriterion("investment_evaluation_check >=", value, "investmentEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andInvestmentEvaluationCheckLessThan(Boolean value) {
            addCriterion("investment_evaluation_check <", value, "investmentEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andInvestmentEvaluationCheckLessThanOrEqualTo(Boolean value) {
            addCriterion("investment_evaluation_check <=", value, "investmentEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andInvestmentEvaluationCheckIn(List<Boolean> values) {
            addCriterion("investment_evaluation_check in", values, "investmentEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andInvestmentEvaluationCheckNotIn(List<Boolean> values) {
            addCriterion("investment_evaluation_check not in", values, "investmentEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andInvestmentEvaluationCheckBetween(Boolean value1, Boolean value2) {
            addCriterion("investment_evaluation_check between", value1, value2, "investmentEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andInvestmentEvaluationCheckNotBetween(Boolean value1, Boolean value2) {
            addCriterion("investment_evaluation_check not between", value1, value2, "investmentEvaluationCheck");
            return (Criteria) this;
        }

        public Criteria andValidityEvaluationDateIsNull() {
            addCriterion("validity_evaluation_date is null");
            return (Criteria) this;
        }

        public Criteria andValidityEvaluationDateIsNotNull() {
            addCriterion("validity_evaluation_date is not null");
            return (Criteria) this;
        }

        public Criteria andValidityEvaluationDateEqualTo(Integer value) {
            addCriterion("validity_evaluation_date =", value, "validityEvaluationDate");
            return (Criteria) this;
        }

        public Criteria andValidityEvaluationDateNotEqualTo(Integer value) {
            addCriterion("validity_evaluation_date <>", value, "validityEvaluationDate");
            return (Criteria) this;
        }

        public Criteria andValidityEvaluationDateGreaterThan(Integer value) {
            addCriterion("validity_evaluation_date >", value, "validityEvaluationDate");
            return (Criteria) this;
        }

        public Criteria andValidityEvaluationDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("validity_evaluation_date >=", value, "validityEvaluationDate");
            return (Criteria) this;
        }

        public Criteria andValidityEvaluationDateLessThan(Integer value) {
            addCriterion("validity_evaluation_date <", value, "validityEvaluationDate");
            return (Criteria) this;
        }

        public Criteria andValidityEvaluationDateLessThanOrEqualTo(Integer value) {
            addCriterion("validity_evaluation_date <=", value, "validityEvaluationDate");
            return (Criteria) this;
        }

        public Criteria andValidityEvaluationDateIn(List<Integer> values) {
            addCriterion("validity_evaluation_date in", values, "validityEvaluationDate");
            return (Criteria) this;
        }

        public Criteria andValidityEvaluationDateNotIn(List<Integer> values) {
            addCriterion("validity_evaluation_date not in", values, "validityEvaluationDate");
            return (Criteria) this;
        }

        public Criteria andValidityEvaluationDateBetween(Integer value1, Integer value2) {
            addCriterion("validity_evaluation_date between", value1, value2, "validityEvaluationDate");
            return (Criteria) this;
        }

        public Criteria andValidityEvaluationDateNotBetween(Integer value1, Integer value2) {
            addCriterion("validity_evaluation_date not between", value1, value2, "validityEvaluationDate");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationSingleMoneyIsNull() {
            addCriterion("conservative_evaluation_single_money is null");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationSingleMoneyIsNotNull() {
            addCriterion("conservative_evaluation_single_money is not null");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationSingleMoneyEqualTo(BigDecimal value) {
            addCriterion("conservative_evaluation_single_money =", value, "conservativeEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationSingleMoneyNotEqualTo(BigDecimal value) {
            addCriterion("conservative_evaluation_single_money <>", value, "conservativeEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationSingleMoneyGreaterThan(BigDecimal value) {
            addCriterion("conservative_evaluation_single_money >", value, "conservativeEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationSingleMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("conservative_evaluation_single_money >=", value, "conservativeEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationSingleMoneyLessThan(BigDecimal value) {
            addCriterion("conservative_evaluation_single_money <", value, "conservativeEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationSingleMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("conservative_evaluation_single_money <=", value, "conservativeEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationSingleMoneyIn(List<BigDecimal> values) {
            addCriterion("conservative_evaluation_single_money in", values, "conservativeEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationSingleMoneyNotIn(List<BigDecimal> values) {
            addCriterion("conservative_evaluation_single_money not in", values, "conservativeEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationSingleMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conservative_evaluation_single_money between", value1, value2, "conservativeEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationSingleMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conservative_evaluation_single_money not between", value1, value2, "conservativeEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationSingleMoneyIsNull() {
            addCriterion("growup_evaluation_single_money is null");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationSingleMoneyIsNotNull() {
            addCriterion("growup_evaluation_single_money is not null");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationSingleMoneyEqualTo(BigDecimal value) {
            addCriterion("growup_evaluation_single_money =", value, "growupEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationSingleMoneyNotEqualTo(BigDecimal value) {
            addCriterion("growup_evaluation_single_money <>", value, "growupEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationSingleMoneyGreaterThan(BigDecimal value) {
            addCriterion("growup_evaluation_single_money >", value, "growupEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationSingleMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("growup_evaluation_single_money >=", value, "growupEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationSingleMoneyLessThan(BigDecimal value) {
            addCriterion("growup_evaluation_single_money <", value, "growupEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationSingleMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("growup_evaluation_single_money <=", value, "growupEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationSingleMoneyIn(List<BigDecimal> values) {
            addCriterion("growup_evaluation_single_money in", values, "growupEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationSingleMoneyNotIn(List<BigDecimal> values) {
            addCriterion("growup_evaluation_single_money not in", values, "growupEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationSingleMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("growup_evaluation_single_money between", value1, value2, "growupEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationSingleMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("growup_evaluation_single_money not between", value1, value2, "growupEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationSingleMoneyIsNull() {
            addCriterion("steady_evaluation_single_money is null");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationSingleMoneyIsNotNull() {
            addCriterion("steady_evaluation_single_money is not null");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationSingleMoneyEqualTo(BigDecimal value) {
            addCriterion("steady_evaluation_single_money =", value, "steadyEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationSingleMoneyNotEqualTo(BigDecimal value) {
            addCriterion("steady_evaluation_single_money <>", value, "steadyEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationSingleMoneyGreaterThan(BigDecimal value) {
            addCriterion("steady_evaluation_single_money >", value, "steadyEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationSingleMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("steady_evaluation_single_money >=", value, "steadyEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationSingleMoneyLessThan(BigDecimal value) {
            addCriterion("steady_evaluation_single_money <", value, "steadyEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationSingleMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("steady_evaluation_single_money <=", value, "steadyEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationSingleMoneyIn(List<BigDecimal> values) {
            addCriterion("steady_evaluation_single_money in", values, "steadyEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationSingleMoneyNotIn(List<BigDecimal> values) {
            addCriterion("steady_evaluation_single_money not in", values, "steadyEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationSingleMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("steady_evaluation_single_money between", value1, value2, "steadyEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationSingleMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("steady_evaluation_single_money not between", value1, value2, "steadyEvaluationSingleMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationSinglMoneyIsNull() {
            addCriterion("enterprising_evaluation_singl_money is null");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationSinglMoneyIsNotNull() {
            addCriterion("enterprising_evaluation_singl_money is not null");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationSinglMoneyEqualTo(BigDecimal value) {
            addCriterion("enterprising_evaluation_singl_money =", value, "enterprisingEvaluationSinglMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationSinglMoneyNotEqualTo(BigDecimal value) {
            addCriterion("enterprising_evaluation_singl_money <>", value, "enterprisingEvaluationSinglMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationSinglMoneyGreaterThan(BigDecimal value) {
            addCriterion("enterprising_evaluation_singl_money >", value, "enterprisingEvaluationSinglMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationSinglMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("enterprising_evaluation_singl_money >=", value, "enterprisingEvaluationSinglMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationSinglMoneyLessThan(BigDecimal value) {
            addCriterion("enterprising_evaluation_singl_money <", value, "enterprisingEvaluationSinglMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationSinglMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("enterprising_evaluation_singl_money <=", value, "enterprisingEvaluationSinglMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationSinglMoneyIn(List<BigDecimal> values) {
            addCriterion("enterprising_evaluation_singl_money in", values, "enterprisingEvaluationSinglMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationSinglMoneyNotIn(List<BigDecimal> values) {
            addCriterion("enterprising_evaluation_singl_money not in", values, "enterprisingEvaluationSinglMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationSinglMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("enterprising_evaluation_singl_money between", value1, value2, "enterprisingEvaluationSinglMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationSinglMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("enterprising_evaluation_singl_money not between", value1, value2, "enterprisingEvaluationSinglMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationPrincipalMoneyIsNull() {
            addCriterion("conservative_evaluation_principal_money is null");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationPrincipalMoneyIsNotNull() {
            addCriterion("conservative_evaluation_principal_money is not null");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationPrincipalMoneyEqualTo(BigDecimal value) {
            addCriterion("conservative_evaluation_principal_money =", value, "conservativeEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationPrincipalMoneyNotEqualTo(BigDecimal value) {
            addCriterion("conservative_evaluation_principal_money <>", value, "conservativeEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationPrincipalMoneyGreaterThan(BigDecimal value) {
            addCriterion("conservative_evaluation_principal_money >", value, "conservativeEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationPrincipalMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("conservative_evaluation_principal_money >=", value, "conservativeEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationPrincipalMoneyLessThan(BigDecimal value) {
            addCriterion("conservative_evaluation_principal_money <", value, "conservativeEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationPrincipalMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("conservative_evaluation_principal_money <=", value, "conservativeEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationPrincipalMoneyIn(List<BigDecimal> values) {
            addCriterion("conservative_evaluation_principal_money in", values, "conservativeEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationPrincipalMoneyNotIn(List<BigDecimal> values) {
            addCriterion("conservative_evaluation_principal_money not in", values, "conservativeEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationPrincipalMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conservative_evaluation_principal_money between", value1, value2, "conservativeEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andConservativeEvaluationPrincipalMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("conservative_evaluation_principal_money not between", value1, value2, "conservativeEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationPrincipalMoneyIsNull() {
            addCriterion("growup_evaluation_principal_money is null");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationPrincipalMoneyIsNotNull() {
            addCriterion("growup_evaluation_principal_money is not null");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationPrincipalMoneyEqualTo(BigDecimal value) {
            addCriterion("growup_evaluation_principal_money =", value, "growupEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationPrincipalMoneyNotEqualTo(BigDecimal value) {
            addCriterion("growup_evaluation_principal_money <>", value, "growupEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationPrincipalMoneyGreaterThan(BigDecimal value) {
            addCriterion("growup_evaluation_principal_money >", value, "growupEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationPrincipalMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("growup_evaluation_principal_money >=", value, "growupEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationPrincipalMoneyLessThan(BigDecimal value) {
            addCriterion("growup_evaluation_principal_money <", value, "growupEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationPrincipalMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("growup_evaluation_principal_money <=", value, "growupEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationPrincipalMoneyIn(List<BigDecimal> values) {
            addCriterion("growup_evaluation_principal_money in", values, "growupEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationPrincipalMoneyNotIn(List<BigDecimal> values) {
            addCriterion("growup_evaluation_principal_money not in", values, "growupEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationPrincipalMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("growup_evaluation_principal_money between", value1, value2, "growupEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andGrowupEvaluationPrincipalMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("growup_evaluation_principal_money not between", value1, value2, "growupEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationPrincipalMoneyIsNull() {
            addCriterion("steady_evaluation_principal_money is null");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationPrincipalMoneyIsNotNull() {
            addCriterion("steady_evaluation_principal_money is not null");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationPrincipalMoneyEqualTo(BigDecimal value) {
            addCriterion("steady_evaluation_principal_money =", value, "steadyEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationPrincipalMoneyNotEqualTo(BigDecimal value) {
            addCriterion("steady_evaluation_principal_money <>", value, "steadyEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationPrincipalMoneyGreaterThan(BigDecimal value) {
            addCriterion("steady_evaluation_principal_money >", value, "steadyEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationPrincipalMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("steady_evaluation_principal_money >=", value, "steadyEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationPrincipalMoneyLessThan(BigDecimal value) {
            addCriterion("steady_evaluation_principal_money <", value, "steadyEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationPrincipalMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("steady_evaluation_principal_money <=", value, "steadyEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationPrincipalMoneyIn(List<BigDecimal> values) {
            addCriterion("steady_evaluation_principal_money in", values, "steadyEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationPrincipalMoneyNotIn(List<BigDecimal> values) {
            addCriterion("steady_evaluation_principal_money not in", values, "steadyEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationPrincipalMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("steady_evaluation_principal_money between", value1, value2, "steadyEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andSteadyEvaluationPrincipalMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("steady_evaluation_principal_money not between", value1, value2, "steadyEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationPrincipalMoneyIsNull() {
            addCriterion("enterprising_evaluation_principal_money is null");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationPrincipalMoneyIsNotNull() {
            addCriterion("enterprising_evaluation_principal_money is not null");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationPrincipalMoneyEqualTo(BigDecimal value) {
            addCriterion("enterprising_evaluation_principal_money =", value, "enterprisingEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationPrincipalMoneyNotEqualTo(BigDecimal value) {
            addCriterion("enterprising_evaluation_principal_money <>", value, "enterprisingEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationPrincipalMoneyGreaterThan(BigDecimal value) {
            addCriterion("enterprising_evaluation_principal_money >", value, "enterprisingEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationPrincipalMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("enterprising_evaluation_principal_money >=", value, "enterprisingEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationPrincipalMoneyLessThan(BigDecimal value) {
            addCriterion("enterprising_evaluation_principal_money <", value, "enterprisingEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationPrincipalMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("enterprising_evaluation_principal_money <=", value, "enterprisingEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationPrincipalMoneyIn(List<BigDecimal> values) {
            addCriterion("enterprising_evaluation_principal_money in", values, "enterprisingEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationPrincipalMoneyNotIn(List<BigDecimal> values) {
            addCriterion("enterprising_evaluation_principal_money not in", values, "enterprisingEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationPrincipalMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("enterprising_evaluation_principal_money between", value1, value2, "enterprisingEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andEnterprisingEvaluationPrincipalMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("enterprising_evaluation_principal_money not between", value1, value2, "enterprisingEvaluationPrincipalMoney");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalIsNull() {
            addCriterion("bbb_evaluation_proposal is null");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalIsNotNull() {
            addCriterion("bbb_evaluation_proposal is not null");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalEqualTo(String value) {
            addCriterion("bbb_evaluation_proposal =", value, "bbbEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalNotEqualTo(String value) {
            addCriterion("bbb_evaluation_proposal <>", value, "bbbEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalGreaterThan(String value) {
            addCriterion("bbb_evaluation_proposal >", value, "bbbEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalGreaterThanOrEqualTo(String value) {
            addCriterion("bbb_evaluation_proposal >=", value, "bbbEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalLessThan(String value) {
            addCriterion("bbb_evaluation_proposal <", value, "bbbEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalLessThanOrEqualTo(String value) {
            addCriterion("bbb_evaluation_proposal <=", value, "bbbEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalLike(String value) {
            addCriterion("bbb_evaluation_proposal like", value, "bbbEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalNotLike(String value) {
            addCriterion("bbb_evaluation_proposal not like", value, "bbbEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalIn(List<String> values) {
            addCriterion("bbb_evaluation_proposal in", values, "bbbEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalNotIn(List<String> values) {
            addCriterion("bbb_evaluation_proposal not in", values, "bbbEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalBetween(String value1, String value2) {
            addCriterion("bbb_evaluation_proposal between", value1, value2, "bbbEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andBbbEvaluationProposalNotBetween(String value1, String value2) {
            addCriterion("bbb_evaluation_proposal not between", value1, value2, "bbbEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalIsNull() {
            addCriterion("A_evaluation_proposal is null");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalIsNotNull() {
            addCriterion("A_evaluation_proposal is not null");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalEqualTo(String value) {
            addCriterion("A_evaluation_proposal =", value, "aEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalNotEqualTo(String value) {
            addCriterion("A_evaluation_proposal <>", value, "aEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalGreaterThan(String value) {
            addCriterion("A_evaluation_proposal >", value, "aEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalGreaterThanOrEqualTo(String value) {
            addCriterion("A_evaluation_proposal >=", value, "aEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalLessThan(String value) {
            addCriterion("A_evaluation_proposal <", value, "aEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalLessThanOrEqualTo(String value) {
            addCriterion("A_evaluation_proposal <=", value, "aEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalLike(String value) {
            addCriterion("A_evaluation_proposal like", value, "aEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalNotLike(String value) {
            addCriterion("A_evaluation_proposal not like", value, "aEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalIn(List<String> values) {
            addCriterion("A_evaluation_proposal in", values, "aEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalNotIn(List<String> values) {
            addCriterion("A_evaluation_proposal not in", values, "aEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalBetween(String value1, String value2) {
            addCriterion("A_evaluation_proposal between", value1, value2, "aEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAEvaluationProposalNotBetween(String value1, String value2) {
            addCriterion("A_evaluation_proposal not between", value1, value2, "aEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalIsNull() {
            addCriterion("AA0_evaluation_proposal is null");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalIsNotNull() {
            addCriterion("AA0_evaluation_proposal is not null");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalEqualTo(String value) {
            addCriterion("AA0_evaluation_proposal =", value, "aa0EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalNotEqualTo(String value) {
            addCriterion("AA0_evaluation_proposal <>", value, "aa0EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalGreaterThan(String value) {
            addCriterion("AA0_evaluation_proposal >", value, "aa0EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalGreaterThanOrEqualTo(String value) {
            addCriterion("AA0_evaluation_proposal >=", value, "aa0EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalLessThan(String value) {
            addCriterion("AA0_evaluation_proposal <", value, "aa0EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalLessThanOrEqualTo(String value) {
            addCriterion("AA0_evaluation_proposal <=", value, "aa0EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalLike(String value) {
            addCriterion("AA0_evaluation_proposal like", value, "aa0EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalNotLike(String value) {
            addCriterion("AA0_evaluation_proposal not like", value, "aa0EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalIn(List<String> values) {
            addCriterion("AA0_evaluation_proposal in", values, "aa0EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalNotIn(List<String> values) {
            addCriterion("AA0_evaluation_proposal not in", values, "aa0EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalBetween(String value1, String value2) {
            addCriterion("AA0_evaluation_proposal between", value1, value2, "aa0EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa0EvaluationProposalNotBetween(String value1, String value2) {
            addCriterion("AA0_evaluation_proposal not between", value1, value2, "aa0EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalIsNull() {
            addCriterion("AA1_evaluation_proposal is null");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalIsNotNull() {
            addCriterion("AA1_evaluation_proposal is not null");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalEqualTo(String value) {
            addCriterion("AA1_evaluation_proposal =", value, "aa1EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalNotEqualTo(String value) {
            addCriterion("AA1_evaluation_proposal <>", value, "aa1EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalGreaterThan(String value) {
            addCriterion("AA1_evaluation_proposal >", value, "aa1EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalGreaterThanOrEqualTo(String value) {
            addCriterion("AA1_evaluation_proposal >=", value, "aa1EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalLessThan(String value) {
            addCriterion("AA1_evaluation_proposal <", value, "aa1EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalLessThanOrEqualTo(String value) {
            addCriterion("AA1_evaluation_proposal <=", value, "aa1EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalLike(String value) {
            addCriterion("AA1_evaluation_proposal like", value, "aa1EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalNotLike(String value) {
            addCriterion("AA1_evaluation_proposal not like", value, "aa1EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalIn(List<String> values) {
            addCriterion("AA1_evaluation_proposal in", values, "aa1EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalNotIn(List<String> values) {
            addCriterion("AA1_evaluation_proposal not in", values, "aa1EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalBetween(String value1, String value2) {
            addCriterion("AA1_evaluation_proposal between", value1, value2, "aa1EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa1EvaluationProposalNotBetween(String value1, String value2) {
            addCriterion("AA1_evaluation_proposal not between", value1, value2, "aa1EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalIsNull() {
            addCriterion("AA2_evaluation_proposal is null");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalIsNotNull() {
            addCriterion("AA2_evaluation_proposal is not null");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalEqualTo(String value) {
            addCriterion("AA2_evaluation_proposal =", value, "aa2EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalNotEqualTo(String value) {
            addCriterion("AA2_evaluation_proposal <>", value, "aa2EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalGreaterThan(String value) {
            addCriterion("AA2_evaluation_proposal >", value, "aa2EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalGreaterThanOrEqualTo(String value) {
            addCriterion("AA2_evaluation_proposal >=", value, "aa2EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalLessThan(String value) {
            addCriterion("AA2_evaluation_proposal <", value, "aa2EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalLessThanOrEqualTo(String value) {
            addCriterion("AA2_evaluation_proposal <=", value, "aa2EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalLike(String value) {
            addCriterion("AA2_evaluation_proposal like", value, "aa2EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalNotLike(String value) {
            addCriterion("AA2_evaluation_proposal not like", value, "aa2EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalIn(List<String> values) {
            addCriterion("AA2_evaluation_proposal in", values, "aa2EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalNotIn(List<String> values) {
            addCriterion("AA2_evaluation_proposal not in", values, "aa2EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalBetween(String value1, String value2) {
            addCriterion("AA2_evaluation_proposal between", value1, value2, "aa2EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAa2EvaluationProposalNotBetween(String value1, String value2) {
            addCriterion("AA2_evaluation_proposal not between", value1, value2, "aa2EvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalIsNull() {
            addCriterion("AAA_evaluation_proposal is null");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalIsNotNull() {
            addCriterion("AAA_evaluation_proposal is not null");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalEqualTo(String value) {
            addCriterion("AAA_evaluation_proposal =", value, "aaaEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalNotEqualTo(String value) {
            addCriterion("AAA_evaluation_proposal <>", value, "aaaEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalGreaterThan(String value) {
            addCriterion("AAA_evaluation_proposal >", value, "aaaEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalGreaterThanOrEqualTo(String value) {
            addCriterion("AAA_evaluation_proposal >=", value, "aaaEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalLessThan(String value) {
            addCriterion("AAA_evaluation_proposal <", value, "aaaEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalLessThanOrEqualTo(String value) {
            addCriterion("AAA_evaluation_proposal <=", value, "aaaEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalLike(String value) {
            addCriterion("AAA_evaluation_proposal like", value, "aaaEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalNotLike(String value) {
            addCriterion("AAA_evaluation_proposal not like", value, "aaaEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalIn(List<String> values) {
            addCriterion("AAA_evaluation_proposal in", values, "aaaEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalNotIn(List<String> values) {
            addCriterion("AAA_evaluation_proposal not in", values, "aaaEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalBetween(String value1, String value2) {
            addCriterion("AAA_evaluation_proposal between", value1, value2, "aaaEvaluationProposal");
            return (Criteria) this;
        }

        public Criteria andAaaEvaluationProposalNotBetween(String value1, String value2) {
            addCriterion("AAA_evaluation_proposal not between", value1, value2, "aaaEvaluationProposal");
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