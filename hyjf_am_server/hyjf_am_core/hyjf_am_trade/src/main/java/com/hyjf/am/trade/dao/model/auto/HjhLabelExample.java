package com.hyjf.am.trade.dao.model.auto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HjhLabelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhLabelExample() {
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

        protected void addCriterionForJDBCTime(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value.getTime()), property);
        }

        protected void addCriterionForJDBCTime(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Time> timeList = new ArrayList<java.sql.Time>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                timeList.add(new java.sql.Time(iter.next().getTime()));
            }
            addCriterion(condition, timeList, property);
        }

        protected void addCriterionForJDBCTime(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);
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

        public Criteria andLabelNameIsNull() {
            addCriterion("label_name is null");
            return (Criteria) this;
        }

        public Criteria andLabelNameIsNotNull() {
            addCriterion("label_name is not null");
            return (Criteria) this;
        }

        public Criteria andLabelNameEqualTo(String value) {
            addCriterion("label_name =", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotEqualTo(String value) {
            addCriterion("label_name <>", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameGreaterThan(String value) {
            addCriterion("label_name >", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameGreaterThanOrEqualTo(String value) {
            addCriterion("label_name >=", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLessThan(String value) {
            addCriterion("label_name <", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLessThanOrEqualTo(String value) {
            addCriterion("label_name <=", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameLike(String value) {
            addCriterion("label_name like", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotLike(String value) {
            addCriterion("label_name not like", value, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameIn(List<String> values) {
            addCriterion("label_name in", values, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotIn(List<String> values) {
            addCriterion("label_name not in", values, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameBetween(String value1, String value2) {
            addCriterion("label_name between", value1, value2, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelNameNotBetween(String value1, String value2) {
            addCriterion("label_name not between", value1, value2, "labelName");
            return (Criteria) this;
        }

        public Criteria andLabelTermStartIsNull() {
            addCriterion("label_term_start is null");
            return (Criteria) this;
        }

        public Criteria andLabelTermStartIsNotNull() {
            addCriterion("label_term_start is not null");
            return (Criteria) this;
        }

        public Criteria andLabelTermStartEqualTo(Integer value) {
            addCriterion("label_term_start =", value, "labelTermStart");
            return (Criteria) this;
        }

        public Criteria andLabelTermStartNotEqualTo(Integer value) {
            addCriterion("label_term_start <>", value, "labelTermStart");
            return (Criteria) this;
        }

        public Criteria andLabelTermStartGreaterThan(Integer value) {
            addCriterion("label_term_start >", value, "labelTermStart");
            return (Criteria) this;
        }

        public Criteria andLabelTermStartGreaterThanOrEqualTo(Integer value) {
            addCriterion("label_term_start >=", value, "labelTermStart");
            return (Criteria) this;
        }

        public Criteria andLabelTermStartLessThan(Integer value) {
            addCriterion("label_term_start <", value, "labelTermStart");
            return (Criteria) this;
        }

        public Criteria andLabelTermStartLessThanOrEqualTo(Integer value) {
            addCriterion("label_term_start <=", value, "labelTermStart");
            return (Criteria) this;
        }

        public Criteria andLabelTermStartIn(List<Integer> values) {
            addCriterion("label_term_start in", values, "labelTermStart");
            return (Criteria) this;
        }

        public Criteria andLabelTermStartNotIn(List<Integer> values) {
            addCriterion("label_term_start not in", values, "labelTermStart");
            return (Criteria) this;
        }

        public Criteria andLabelTermStartBetween(Integer value1, Integer value2) {
            addCriterion("label_term_start between", value1, value2, "labelTermStart");
            return (Criteria) this;
        }

        public Criteria andLabelTermStartNotBetween(Integer value1, Integer value2) {
            addCriterion("label_term_start not between", value1, value2, "labelTermStart");
            return (Criteria) this;
        }

        public Criteria andLabelTermEndIsNull() {
            addCriterion("label_term_end is null");
            return (Criteria) this;
        }

        public Criteria andLabelTermEndIsNotNull() {
            addCriterion("label_term_end is not null");
            return (Criteria) this;
        }

        public Criteria andLabelTermEndEqualTo(Integer value) {
            addCriterion("label_term_end =", value, "labelTermEnd");
            return (Criteria) this;
        }

        public Criteria andLabelTermEndNotEqualTo(Integer value) {
            addCriterion("label_term_end <>", value, "labelTermEnd");
            return (Criteria) this;
        }

        public Criteria andLabelTermEndGreaterThan(Integer value) {
            addCriterion("label_term_end >", value, "labelTermEnd");
            return (Criteria) this;
        }

        public Criteria andLabelTermEndGreaterThanOrEqualTo(Integer value) {
            addCriterion("label_term_end >=", value, "labelTermEnd");
            return (Criteria) this;
        }

        public Criteria andLabelTermEndLessThan(Integer value) {
            addCriterion("label_term_end <", value, "labelTermEnd");
            return (Criteria) this;
        }

        public Criteria andLabelTermEndLessThanOrEqualTo(Integer value) {
            addCriterion("label_term_end <=", value, "labelTermEnd");
            return (Criteria) this;
        }

        public Criteria andLabelTermEndIn(List<Integer> values) {
            addCriterion("label_term_end in", values, "labelTermEnd");
            return (Criteria) this;
        }

        public Criteria andLabelTermEndNotIn(List<Integer> values) {
            addCriterion("label_term_end not in", values, "labelTermEnd");
            return (Criteria) this;
        }

        public Criteria andLabelTermEndBetween(Integer value1, Integer value2) {
            addCriterion("label_term_end between", value1, value2, "labelTermEnd");
            return (Criteria) this;
        }

        public Criteria andLabelTermEndNotBetween(Integer value1, Integer value2) {
            addCriterion("label_term_end not between", value1, value2, "labelTermEnd");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeIsNull() {
            addCriterion("label_term_type is null");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeIsNotNull() {
            addCriterion("label_term_type is not null");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeEqualTo(String value) {
            addCriterion("label_term_type =", value, "labelTermType");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeNotEqualTo(String value) {
            addCriterion("label_term_type <>", value, "labelTermType");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeGreaterThan(String value) {
            addCriterion("label_term_type >", value, "labelTermType");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeGreaterThanOrEqualTo(String value) {
            addCriterion("label_term_type >=", value, "labelTermType");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeLessThan(String value) {
            addCriterion("label_term_type <", value, "labelTermType");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeLessThanOrEqualTo(String value) {
            addCriterion("label_term_type <=", value, "labelTermType");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeLike(String value) {
            addCriterion("label_term_type like", value, "labelTermType");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeNotLike(String value) {
            addCriterion("label_term_type not like", value, "labelTermType");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeIn(List<String> values) {
            addCriterion("label_term_type in", values, "labelTermType");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeNotIn(List<String> values) {
            addCriterion("label_term_type not in", values, "labelTermType");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeBetween(String value1, String value2) {
            addCriterion("label_term_type between", value1, value2, "labelTermType");
            return (Criteria) this;
        }

        public Criteria andLabelTermTypeNotBetween(String value1, String value2) {
            addCriterion("label_term_type not between", value1, value2, "labelTermType");
            return (Criteria) this;
        }

        public Criteria andLabelAprStartIsNull() {
            addCriterion("label_apr_start is null");
            return (Criteria) this;
        }

        public Criteria andLabelAprStartIsNotNull() {
            addCriterion("label_apr_start is not null");
            return (Criteria) this;
        }

        public Criteria andLabelAprStartEqualTo(BigDecimal value) {
            addCriterion("label_apr_start =", value, "labelAprStart");
            return (Criteria) this;
        }

        public Criteria andLabelAprStartNotEqualTo(BigDecimal value) {
            addCriterion("label_apr_start <>", value, "labelAprStart");
            return (Criteria) this;
        }

        public Criteria andLabelAprStartGreaterThan(BigDecimal value) {
            addCriterion("label_apr_start >", value, "labelAprStart");
            return (Criteria) this;
        }

        public Criteria andLabelAprStartGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("label_apr_start >=", value, "labelAprStart");
            return (Criteria) this;
        }

        public Criteria andLabelAprStartLessThan(BigDecimal value) {
            addCriterion("label_apr_start <", value, "labelAprStart");
            return (Criteria) this;
        }

        public Criteria andLabelAprStartLessThanOrEqualTo(BigDecimal value) {
            addCriterion("label_apr_start <=", value, "labelAprStart");
            return (Criteria) this;
        }

        public Criteria andLabelAprStartIn(List<BigDecimal> values) {
            addCriterion("label_apr_start in", values, "labelAprStart");
            return (Criteria) this;
        }

        public Criteria andLabelAprStartNotIn(List<BigDecimal> values) {
            addCriterion("label_apr_start not in", values, "labelAprStart");
            return (Criteria) this;
        }

        public Criteria andLabelAprStartBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("label_apr_start between", value1, value2, "labelAprStart");
            return (Criteria) this;
        }

        public Criteria andLabelAprStartNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("label_apr_start not between", value1, value2, "labelAprStart");
            return (Criteria) this;
        }

        public Criteria andLabelAprEndIsNull() {
            addCriterion("label_apr_end is null");
            return (Criteria) this;
        }

        public Criteria andLabelAprEndIsNotNull() {
            addCriterion("label_apr_end is not null");
            return (Criteria) this;
        }

        public Criteria andLabelAprEndEqualTo(BigDecimal value) {
            addCriterion("label_apr_end =", value, "labelAprEnd");
            return (Criteria) this;
        }

        public Criteria andLabelAprEndNotEqualTo(BigDecimal value) {
            addCriterion("label_apr_end <>", value, "labelAprEnd");
            return (Criteria) this;
        }

        public Criteria andLabelAprEndGreaterThan(BigDecimal value) {
            addCriterion("label_apr_end >", value, "labelAprEnd");
            return (Criteria) this;
        }

        public Criteria andLabelAprEndGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("label_apr_end >=", value, "labelAprEnd");
            return (Criteria) this;
        }

        public Criteria andLabelAprEndLessThan(BigDecimal value) {
            addCriterion("label_apr_end <", value, "labelAprEnd");
            return (Criteria) this;
        }

        public Criteria andLabelAprEndLessThanOrEqualTo(BigDecimal value) {
            addCriterion("label_apr_end <=", value, "labelAprEnd");
            return (Criteria) this;
        }

        public Criteria andLabelAprEndIn(List<BigDecimal> values) {
            addCriterion("label_apr_end in", values, "labelAprEnd");
            return (Criteria) this;
        }

        public Criteria andLabelAprEndNotIn(List<BigDecimal> values) {
            addCriterion("label_apr_end not in", values, "labelAprEnd");
            return (Criteria) this;
        }

        public Criteria andLabelAprEndBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("label_apr_end between", value1, value2, "labelAprEnd");
            return (Criteria) this;
        }

        public Criteria andLabelAprEndNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("label_apr_end not between", value1, value2, "labelAprEnd");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIsNull() {
            addCriterion("borrow_style is null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIsNotNull() {
            addCriterion("borrow_style is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleEqualTo(String value) {
            addCriterion("borrow_style =", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotEqualTo(String value) {
            addCriterion("borrow_style <>", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleGreaterThan(String value) {
            addCriterion("borrow_style >", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_style >=", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLessThan(String value) {
            addCriterion("borrow_style <", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLessThanOrEqualTo(String value) {
            addCriterion("borrow_style <=", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLike(String value) {
            addCriterion("borrow_style like", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotLike(String value) {
            addCriterion("borrow_style not like", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIn(List<String> values) {
            addCriterion("borrow_style in", values, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotIn(List<String> values) {
            addCriterion("borrow_style not in", values, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleBetween(String value1, String value2) {
            addCriterion("borrow_style between", value1, value2, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotBetween(String value1, String value2) {
            addCriterion("borrow_style not between", value1, value2, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameIsNull() {
            addCriterion("borrow_style_name is null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameIsNotNull() {
            addCriterion("borrow_style_name is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameEqualTo(String value) {
            addCriterion("borrow_style_name =", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameNotEqualTo(String value) {
            addCriterion("borrow_style_name <>", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameGreaterThan(String value) {
            addCriterion("borrow_style_name >", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_style_name >=", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameLessThan(String value) {
            addCriterion("borrow_style_name <", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameLessThanOrEqualTo(String value) {
            addCriterion("borrow_style_name <=", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameLike(String value) {
            addCriterion("borrow_style_name like", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameNotLike(String value) {
            addCriterion("borrow_style_name not like", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameIn(List<String> values) {
            addCriterion("borrow_style_name in", values, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameNotIn(List<String> values) {
            addCriterion("borrow_style_name not in", values, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameBetween(String value1, String value2) {
            addCriterion("borrow_style_name between", value1, value2, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameNotBetween(String value1, String value2) {
            addCriterion("borrow_style_name not between", value1, value2, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountStartIsNull() {
            addCriterion("label_payment_account_start is null");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountStartIsNotNull() {
            addCriterion("label_payment_account_start is not null");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountStartEqualTo(BigDecimal value) {
            addCriterion("label_payment_account_start =", value, "labelPaymentAccountStart");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountStartNotEqualTo(BigDecimal value) {
            addCriterion("label_payment_account_start <>", value, "labelPaymentAccountStart");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountStartGreaterThan(BigDecimal value) {
            addCriterion("label_payment_account_start >", value, "labelPaymentAccountStart");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountStartGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("label_payment_account_start >=", value, "labelPaymentAccountStart");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountStartLessThan(BigDecimal value) {
            addCriterion("label_payment_account_start <", value, "labelPaymentAccountStart");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountStartLessThanOrEqualTo(BigDecimal value) {
            addCriterion("label_payment_account_start <=", value, "labelPaymentAccountStart");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountStartIn(List<BigDecimal> values) {
            addCriterion("label_payment_account_start in", values, "labelPaymentAccountStart");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountStartNotIn(List<BigDecimal> values) {
            addCriterion("label_payment_account_start not in", values, "labelPaymentAccountStart");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountStartBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("label_payment_account_start between", value1, value2, "labelPaymentAccountStart");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountStartNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("label_payment_account_start not between", value1, value2, "labelPaymentAccountStart");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountEndIsNull() {
            addCriterion("label_payment_account_end is null");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountEndIsNotNull() {
            addCriterion("label_payment_account_end is not null");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountEndEqualTo(BigDecimal value) {
            addCriterion("label_payment_account_end =", value, "labelPaymentAccountEnd");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountEndNotEqualTo(BigDecimal value) {
            addCriterion("label_payment_account_end <>", value, "labelPaymentAccountEnd");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountEndGreaterThan(BigDecimal value) {
            addCriterion("label_payment_account_end >", value, "labelPaymentAccountEnd");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountEndGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("label_payment_account_end >=", value, "labelPaymentAccountEnd");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountEndLessThan(BigDecimal value) {
            addCriterion("label_payment_account_end <", value, "labelPaymentAccountEnd");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountEndLessThanOrEqualTo(BigDecimal value) {
            addCriterion("label_payment_account_end <=", value, "labelPaymentAccountEnd");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountEndIn(List<BigDecimal> values) {
            addCriterion("label_payment_account_end in", values, "labelPaymentAccountEnd");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountEndNotIn(List<BigDecimal> values) {
            addCriterion("label_payment_account_end not in", values, "labelPaymentAccountEnd");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountEndBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("label_payment_account_end between", value1, value2, "labelPaymentAccountEnd");
            return (Criteria) this;
        }

        public Criteria andLabelPaymentAccountEndNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("label_payment_account_end not between", value1, value2, "labelPaymentAccountEnd");
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

        public Criteria andInstNameIsNull() {
            addCriterion("inst_name is null");
            return (Criteria) this;
        }

        public Criteria andInstNameIsNotNull() {
            addCriterion("inst_name is not null");
            return (Criteria) this;
        }

        public Criteria andInstNameEqualTo(String value) {
            addCriterion("inst_name =", value, "instName");
            return (Criteria) this;
        }

        public Criteria andInstNameNotEqualTo(String value) {
            addCriterion("inst_name <>", value, "instName");
            return (Criteria) this;
        }

        public Criteria andInstNameGreaterThan(String value) {
            addCriterion("inst_name >", value, "instName");
            return (Criteria) this;
        }

        public Criteria andInstNameGreaterThanOrEqualTo(String value) {
            addCriterion("inst_name >=", value, "instName");
            return (Criteria) this;
        }

        public Criteria andInstNameLessThan(String value) {
            addCriterion("inst_name <", value, "instName");
            return (Criteria) this;
        }

        public Criteria andInstNameLessThanOrEqualTo(String value) {
            addCriterion("inst_name <=", value, "instName");
            return (Criteria) this;
        }

        public Criteria andInstNameLike(String value) {
            addCriterion("inst_name like", value, "instName");
            return (Criteria) this;
        }

        public Criteria andInstNameNotLike(String value) {
            addCriterion("inst_name not like", value, "instName");
            return (Criteria) this;
        }

        public Criteria andInstNameIn(List<String> values) {
            addCriterion("inst_name in", values, "instName");
            return (Criteria) this;
        }

        public Criteria andInstNameNotIn(List<String> values) {
            addCriterion("inst_name not in", values, "instName");
            return (Criteria) this;
        }

        public Criteria andInstNameBetween(String value1, String value2) {
            addCriterion("inst_name between", value1, value2, "instName");
            return (Criteria) this;
        }

        public Criteria andInstNameNotBetween(String value1, String value2) {
            addCriterion("inst_name not between", value1, value2, "instName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIsNull() {
            addCriterion("asset_type is null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIsNotNull() {
            addCriterion("asset_type is not null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeEqualTo(Integer value) {
            addCriterion("asset_type =", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotEqualTo(Integer value) {
            addCriterion("asset_type <>", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThan(Integer value) {
            addCriterion("asset_type >", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("asset_type >=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThan(Integer value) {
            addCriterion("asset_type <", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThanOrEqualTo(Integer value) {
            addCriterion("asset_type <=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIn(List<Integer> values) {
            addCriterion("asset_type in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotIn(List<Integer> values) {
            addCriterion("asset_type not in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeBetween(Integer value1, Integer value2) {
            addCriterion("asset_type between", value1, value2, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("asset_type not between", value1, value2, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameIsNull() {
            addCriterion("asset_type_name is null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameIsNotNull() {
            addCriterion("asset_type_name is not null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameEqualTo(String value) {
            addCriterion("asset_type_name =", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameNotEqualTo(String value) {
            addCriterion("asset_type_name <>", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameGreaterThan(String value) {
            addCriterion("asset_type_name >", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("asset_type_name >=", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameLessThan(String value) {
            addCriterion("asset_type_name <", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameLessThanOrEqualTo(String value) {
            addCriterion("asset_type_name <=", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameLike(String value) {
            addCriterion("asset_type_name like", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameNotLike(String value) {
            addCriterion("asset_type_name not like", value, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameIn(List<String> values) {
            addCriterion("asset_type_name in", values, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameNotIn(List<String> values) {
            addCriterion("asset_type_name not in", values, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameBetween(String value1, String value2) {
            addCriterion("asset_type_name between", value1, value2, "assetTypeName");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNameNotBetween(String value1, String value2) {
            addCriterion("asset_type_name not between", value1, value2, "assetTypeName");
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

        public Criteria andProjectTypeEqualTo(Integer value) {
            addCriterion("project_type =", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotEqualTo(Integer value) {
            addCriterion("project_type <>", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThan(Integer value) {
            addCriterion("project_type >", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_type >=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThan(Integer value) {
            addCriterion("project_type <", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThanOrEqualTo(Integer value) {
            addCriterion("project_type <=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIn(List<Integer> values) {
            addCriterion("project_type in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotIn(List<Integer> values) {
            addCriterion("project_type not in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeBetween(Integer value1, Integer value2) {
            addCriterion("project_type between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("project_type not between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameIsNull() {
            addCriterion("project_type_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameIsNotNull() {
            addCriterion("project_type_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameEqualTo(String value) {
            addCriterion("project_type_name =", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameNotEqualTo(String value) {
            addCriterion("project_type_name <>", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameGreaterThan(String value) {
            addCriterion("project_type_name >", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_type_name >=", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameLessThan(String value) {
            addCriterion("project_type_name <", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameLessThanOrEqualTo(String value) {
            addCriterion("project_type_name <=", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameLike(String value) {
            addCriterion("project_type_name like", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameNotLike(String value) {
            addCriterion("project_type_name not like", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameIn(List<String> values) {
            addCriterion("project_type_name in", values, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameNotIn(List<String> values) {
            addCriterion("project_type_name not in", values, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameBetween(String value1, String value2) {
            addCriterion("project_type_name between", value1, value2, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameNotBetween(String value1, String value2) {
            addCriterion("project_type_name not between", value1, value2, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andIsCreditIsNull() {
            addCriterion("is_credit is null");
            return (Criteria) this;
        }

        public Criteria andIsCreditIsNotNull() {
            addCriterion("is_credit is not null");
            return (Criteria) this;
        }

        public Criteria andIsCreditEqualTo(Integer value) {
            addCriterion("is_credit =", value, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditNotEqualTo(Integer value) {
            addCriterion("is_credit <>", value, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditGreaterThan(Integer value) {
            addCriterion("is_credit >", value, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_credit >=", value, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditLessThan(Integer value) {
            addCriterion("is_credit <", value, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditLessThanOrEqualTo(Integer value) {
            addCriterion("is_credit <=", value, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditIn(List<Integer> values) {
            addCriterion("is_credit in", values, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditNotIn(List<Integer> values) {
            addCriterion("is_credit not in", values, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditBetween(Integer value1, Integer value2) {
            addCriterion("is_credit between", value1, value2, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsCreditNotBetween(Integer value1, Integer value2) {
            addCriterion("is_credit not between", value1, value2, "isCredit");
            return (Criteria) this;
        }

        public Criteria andIsLateIsNull() {
            addCriterion("is_late is null");
            return (Criteria) this;
        }

        public Criteria andIsLateIsNotNull() {
            addCriterion("is_late is not null");
            return (Criteria) this;
        }

        public Criteria andIsLateEqualTo(Integer value) {
            addCriterion("is_late =", value, "isLate");
            return (Criteria) this;
        }

        public Criteria andIsLateNotEqualTo(Integer value) {
            addCriterion("is_late <>", value, "isLate");
            return (Criteria) this;
        }

        public Criteria andIsLateGreaterThan(Integer value) {
            addCriterion("is_late >", value, "isLate");
            return (Criteria) this;
        }

        public Criteria andIsLateGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_late >=", value, "isLate");
            return (Criteria) this;
        }

        public Criteria andIsLateLessThan(Integer value) {
            addCriterion("is_late <", value, "isLate");
            return (Criteria) this;
        }

        public Criteria andIsLateLessThanOrEqualTo(Integer value) {
            addCriterion("is_late <=", value, "isLate");
            return (Criteria) this;
        }

        public Criteria andIsLateIn(List<Integer> values) {
            addCriterion("is_late in", values, "isLate");
            return (Criteria) this;
        }

        public Criteria andIsLateNotIn(List<Integer> values) {
            addCriterion("is_late not in", values, "isLate");
            return (Criteria) this;
        }

        public Criteria andIsLateBetween(Integer value1, Integer value2) {
            addCriterion("is_late between", value1, value2, "isLate");
            return (Criteria) this;
        }

        public Criteria andIsLateNotBetween(Integer value1, Integer value2) {
            addCriterion("is_late not between", value1, value2, "isLate");
            return (Criteria) this;
        }

        public Criteria andCreditSumMaxIsNull() {
            addCriterion("credit_sum_max is null");
            return (Criteria) this;
        }

        public Criteria andCreditSumMaxIsNotNull() {
            addCriterion("credit_sum_max is not null");
            return (Criteria) this;
        }

        public Criteria andCreditSumMaxEqualTo(Integer value) {
            addCriterion("credit_sum_max =", value, "creditSumMax");
            return (Criteria) this;
        }

        public Criteria andCreditSumMaxNotEqualTo(Integer value) {
            addCriterion("credit_sum_max <>", value, "creditSumMax");
            return (Criteria) this;
        }

        public Criteria andCreditSumMaxGreaterThan(Integer value) {
            addCriterion("credit_sum_max >", value, "creditSumMax");
            return (Criteria) this;
        }

        public Criteria andCreditSumMaxGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit_sum_max >=", value, "creditSumMax");
            return (Criteria) this;
        }

        public Criteria andCreditSumMaxLessThan(Integer value) {
            addCriterion("credit_sum_max <", value, "creditSumMax");
            return (Criteria) this;
        }

        public Criteria andCreditSumMaxLessThanOrEqualTo(Integer value) {
            addCriterion("credit_sum_max <=", value, "creditSumMax");
            return (Criteria) this;
        }

        public Criteria andCreditSumMaxIn(List<Integer> values) {
            addCriterion("credit_sum_max in", values, "creditSumMax");
            return (Criteria) this;
        }

        public Criteria andCreditSumMaxNotIn(List<Integer> values) {
            addCriterion("credit_sum_max not in", values, "creditSumMax");
            return (Criteria) this;
        }

        public Criteria andCreditSumMaxBetween(Integer value1, Integer value2) {
            addCriterion("credit_sum_max between", value1, value2, "creditSumMax");
            return (Criteria) this;
        }

        public Criteria andCreditSumMaxNotBetween(Integer value1, Integer value2) {
            addCriterion("credit_sum_max not between", value1, value2, "creditSumMax");
            return (Criteria) this;
        }

        public Criteria andPushTimeStartIsNull() {
            addCriterion("push_time_start is null");
            return (Criteria) this;
        }

        public Criteria andPushTimeStartIsNotNull() {
            addCriterion("push_time_start is not null");
            return (Criteria) this;
        }

        public Criteria andPushTimeStartEqualTo(Date value) {
            addCriterionForJDBCTime("push_time_start =", value, "pushTimeStart");
            return (Criteria) this;
        }

        public Criteria andPushTimeStartNotEqualTo(Date value) {
            addCriterionForJDBCTime("push_time_start <>", value, "pushTimeStart");
            return (Criteria) this;
        }

        public Criteria andPushTimeStartGreaterThan(Date value) {
            addCriterionForJDBCTime("push_time_start >", value, "pushTimeStart");
            return (Criteria) this;
        }

        public Criteria andPushTimeStartGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("push_time_start >=", value, "pushTimeStart");
            return (Criteria) this;
        }

        public Criteria andPushTimeStartLessThan(Date value) {
            addCriterionForJDBCTime("push_time_start <", value, "pushTimeStart");
            return (Criteria) this;
        }

        public Criteria andPushTimeStartLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("push_time_start <=", value, "pushTimeStart");
            return (Criteria) this;
        }

        public Criteria andPushTimeStartIn(List<Date> values) {
            addCriterionForJDBCTime("push_time_start in", values, "pushTimeStart");
            return (Criteria) this;
        }

        public Criteria andPushTimeStartNotIn(List<Date> values) {
            addCriterionForJDBCTime("push_time_start not in", values, "pushTimeStart");
            return (Criteria) this;
        }

        public Criteria andPushTimeStartBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("push_time_start between", value1, value2, "pushTimeStart");
            return (Criteria) this;
        }

        public Criteria andPushTimeStartNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("push_time_start not between", value1, value2, "pushTimeStart");
            return (Criteria) this;
        }

        public Criteria andPushTimeEndIsNull() {
            addCriterion("push_time_end is null");
            return (Criteria) this;
        }

        public Criteria andPushTimeEndIsNotNull() {
            addCriterion("push_time_end is not null");
            return (Criteria) this;
        }

        public Criteria andPushTimeEndEqualTo(Date value) {
            addCriterionForJDBCTime("push_time_end =", value, "pushTimeEnd");
            return (Criteria) this;
        }

        public Criteria andPushTimeEndNotEqualTo(Date value) {
            addCriterionForJDBCTime("push_time_end <>", value, "pushTimeEnd");
            return (Criteria) this;
        }

        public Criteria andPushTimeEndGreaterThan(Date value) {
            addCriterionForJDBCTime("push_time_end >", value, "pushTimeEnd");
            return (Criteria) this;
        }

        public Criteria andPushTimeEndGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("push_time_end >=", value, "pushTimeEnd");
            return (Criteria) this;
        }

        public Criteria andPushTimeEndLessThan(Date value) {
            addCriterionForJDBCTime("push_time_end <", value, "pushTimeEnd");
            return (Criteria) this;
        }

        public Criteria andPushTimeEndLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("push_time_end <=", value, "pushTimeEnd");
            return (Criteria) this;
        }

        public Criteria andPushTimeEndIn(List<Date> values) {
            addCriterionForJDBCTime("push_time_end in", values, "pushTimeEnd");
            return (Criteria) this;
        }

        public Criteria andPushTimeEndNotIn(List<Date> values) {
            addCriterionForJDBCTime("push_time_end not in", values, "pushTimeEnd");
            return (Criteria) this;
        }

        public Criteria andPushTimeEndBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("push_time_end between", value1, value2, "pushTimeEnd");
            return (Criteria) this;
        }

        public Criteria andPushTimeEndNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("push_time_end not between", value1, value2, "pushTimeEnd");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysStartIsNull() {
            addCriterion("remaining_days_start is null");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysStartIsNotNull() {
            addCriterion("remaining_days_start is not null");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysStartEqualTo(Integer value) {
            addCriterion("remaining_days_start =", value, "remainingDaysStart");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysStartNotEqualTo(Integer value) {
            addCriterion("remaining_days_start <>", value, "remainingDaysStart");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysStartGreaterThan(Integer value) {
            addCriterion("remaining_days_start >", value, "remainingDaysStart");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysStartGreaterThanOrEqualTo(Integer value) {
            addCriterion("remaining_days_start >=", value, "remainingDaysStart");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysStartLessThan(Integer value) {
            addCriterion("remaining_days_start <", value, "remainingDaysStart");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysStartLessThanOrEqualTo(Integer value) {
            addCriterion("remaining_days_start <=", value, "remainingDaysStart");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysStartIn(List<Integer> values) {
            addCriterion("remaining_days_start in", values, "remainingDaysStart");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysStartNotIn(List<Integer> values) {
            addCriterion("remaining_days_start not in", values, "remainingDaysStart");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysStartBetween(Integer value1, Integer value2) {
            addCriterion("remaining_days_start between", value1, value2, "remainingDaysStart");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysStartNotBetween(Integer value1, Integer value2) {
            addCriterion("remaining_days_start not between", value1, value2, "remainingDaysStart");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysEndIsNull() {
            addCriterion("remaining_days_end is null");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysEndIsNotNull() {
            addCriterion("remaining_days_end is not null");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysEndEqualTo(Integer value) {
            addCriterion("remaining_days_end =", value, "remainingDaysEnd");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysEndNotEqualTo(Integer value) {
            addCriterion("remaining_days_end <>", value, "remainingDaysEnd");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysEndGreaterThan(Integer value) {
            addCriterion("remaining_days_end >", value, "remainingDaysEnd");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysEndGreaterThanOrEqualTo(Integer value) {
            addCriterion("remaining_days_end >=", value, "remainingDaysEnd");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysEndLessThan(Integer value) {
            addCriterion("remaining_days_end <", value, "remainingDaysEnd");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysEndLessThanOrEqualTo(Integer value) {
            addCriterion("remaining_days_end <=", value, "remainingDaysEnd");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysEndIn(List<Integer> values) {
            addCriterion("remaining_days_end in", values, "remainingDaysEnd");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysEndNotIn(List<Integer> values) {
            addCriterion("remaining_days_end not in", values, "remainingDaysEnd");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysEndBetween(Integer value1, Integer value2) {
            addCriterion("remaining_days_end between", value1, value2, "remainingDaysEnd");
            return (Criteria) this;
        }

        public Criteria andRemainingDaysEndNotBetween(Integer value1, Integer value2) {
            addCriterion("remaining_days_end not between", value1, value2, "remainingDaysEnd");
            return (Criteria) this;
        }

        public Criteria andLabelStateIsNull() {
            addCriterion("label_state is null");
            return (Criteria) this;
        }

        public Criteria andLabelStateIsNotNull() {
            addCriterion("label_state is not null");
            return (Criteria) this;
        }

        public Criteria andLabelStateEqualTo(Integer value) {
            addCriterion("label_state =", value, "labelState");
            return (Criteria) this;
        }

        public Criteria andLabelStateNotEqualTo(Integer value) {
            addCriterion("label_state <>", value, "labelState");
            return (Criteria) this;
        }

        public Criteria andLabelStateGreaterThan(Integer value) {
            addCriterion("label_state >", value, "labelState");
            return (Criteria) this;
        }

        public Criteria andLabelStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("label_state >=", value, "labelState");
            return (Criteria) this;
        }

        public Criteria andLabelStateLessThan(Integer value) {
            addCriterion("label_state <", value, "labelState");
            return (Criteria) this;
        }

        public Criteria andLabelStateLessThanOrEqualTo(Integer value) {
            addCriterion("label_state <=", value, "labelState");
            return (Criteria) this;
        }

        public Criteria andLabelStateIn(List<Integer> values) {
            addCriterion("label_state in", values, "labelState");
            return (Criteria) this;
        }

        public Criteria andLabelStateNotIn(List<Integer> values) {
            addCriterion("label_state not in", values, "labelState");
            return (Criteria) this;
        }

        public Criteria andLabelStateBetween(Integer value1, Integer value2) {
            addCriterion("label_state between", value1, value2, "labelState");
            return (Criteria) this;
        }

        public Criteria andLabelStateNotBetween(Integer value1, Integer value2) {
            addCriterion("label_state not between", value1, value2, "labelState");
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