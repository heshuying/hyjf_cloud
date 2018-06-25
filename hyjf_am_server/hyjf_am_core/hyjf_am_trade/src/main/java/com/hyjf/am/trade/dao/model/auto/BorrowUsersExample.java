package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class BorrowUsersExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public BorrowUsersExample() {
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

        public Criteria andBorrowNidIsNull() {
            addCriterion("borrow_nid is null");
            return (Criteria) this;
        }

        public Criteria andBorrowNidIsNotNull() {
            addCriterion("borrow_nid is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowNidEqualTo(String value) {
            addCriterion("borrow_nid =", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotEqualTo(String value) {
            addCriterion("borrow_nid <>", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidGreaterThan(String value) {
            addCriterion("borrow_nid >", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_nid >=", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLessThan(String value) {
            addCriterion("borrow_nid <", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLessThanOrEqualTo(String value) {
            addCriterion("borrow_nid <=", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidLike(String value) {
            addCriterion("borrow_nid like", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotLike(String value) {
            addCriterion("borrow_nid not like", value, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidIn(List<String> values) {
            addCriterion("borrow_nid in", values, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotIn(List<String> values) {
            addCriterion("borrow_nid not in", values, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidBetween(String value1, String value2) {
            addCriterion("borrow_nid between", value1, value2, "borrowNid");
            return (Criteria) this;
        }

        public Criteria andBorrowNidNotBetween(String value1, String value2) {
            addCriterion("borrow_nid not between", value1, value2, "borrowNid");
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

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameIsNull() {
            addCriterion("currency_name is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameIsNotNull() {
            addCriterion("currency_name is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameEqualTo(String value) {
            addCriterion("currency_name =", value, "currencyName");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameNotEqualTo(String value) {
            addCriterion("currency_name <>", value, "currencyName");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameGreaterThan(String value) {
            addCriterion("currency_name >", value, "currencyName");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameGreaterThanOrEqualTo(String value) {
            addCriterion("currency_name >=", value, "currencyName");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameLessThan(String value) {
            addCriterion("currency_name <", value, "currencyName");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameLessThanOrEqualTo(String value) {
            addCriterion("currency_name <=", value, "currencyName");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameLike(String value) {
            addCriterion("currency_name like", value, "currencyName");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameNotLike(String value) {
            addCriterion("currency_name not like", value, "currencyName");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameIn(List<String> values) {
            addCriterion("currency_name in", values, "currencyName");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameNotIn(List<String> values) {
            addCriterion("currency_name not in", values, "currencyName");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameBetween(String value1, String value2) {
            addCriterion("currency_name between", value1, value2, "currencyName");
            return (Criteria) this;
        }

        public Criteria andCurrencyNameNotBetween(String value1, String value2) {
            addCriterion("currency_name not between", value1, value2, "currencyName");
            return (Criteria) this;
        }

        public Criteria andRegCaptialIsNull() {
            addCriterion("reg_captial is null");
            return (Criteria) this;
        }

        public Criteria andRegCaptialIsNotNull() {
            addCriterion("reg_captial is not null");
            return (Criteria) this;
        }

        public Criteria andRegCaptialEqualTo(String value) {
            addCriterion("reg_captial =", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialNotEqualTo(String value) {
            addCriterion("reg_captial <>", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialGreaterThan(String value) {
            addCriterion("reg_captial >", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialGreaterThanOrEqualTo(String value) {
            addCriterion("reg_captial >=", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialLessThan(String value) {
            addCriterion("reg_captial <", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialLessThanOrEqualTo(String value) {
            addCriterion("reg_captial <=", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialLike(String value) {
            addCriterion("reg_captial like", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialNotLike(String value) {
            addCriterion("reg_captial not like", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialIn(List<String> values) {
            addCriterion("reg_captial in", values, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialNotIn(List<String> values) {
            addCriterion("reg_captial not in", values, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialBetween(String value1, String value2) {
            addCriterion("reg_captial between", value1, value2, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialNotBetween(String value1, String value2) {
            addCriterion("reg_captial not between", value1, value2, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andIndustryIsNull() {
            addCriterion("industry is null");
            return (Criteria) this;
        }

        public Criteria andIndustryIsNotNull() {
            addCriterion("industry is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryEqualTo(String value) {
            addCriterion("industry =", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotEqualTo(String value) {
            addCriterion("industry <>", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryGreaterThan(String value) {
            addCriterion("industry >", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryGreaterThanOrEqualTo(String value) {
            addCriterion("industry >=", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLessThan(String value) {
            addCriterion("industry <", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLessThanOrEqualTo(String value) {
            addCriterion("industry <=", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLike(String value) {
            addCriterion("industry like", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotLike(String value) {
            addCriterion("industry not like", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryIn(List<String> values) {
            addCriterion("industry in", values, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotIn(List<String> values) {
            addCriterion("industry not in", values, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryBetween(String value1, String value2) {
            addCriterion("industry between", value1, value2, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotBetween(String value1, String value2) {
            addCriterion("industry not between", value1, value2, "industry");
            return (Criteria) this;
        }

        public Criteria andLitigationIsNull() {
            addCriterion("litigation is null");
            return (Criteria) this;
        }

        public Criteria andLitigationIsNotNull() {
            addCriterion("litigation is not null");
            return (Criteria) this;
        }

        public Criteria andLitigationEqualTo(String value) {
            addCriterion("litigation =", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationNotEqualTo(String value) {
            addCriterion("litigation <>", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationGreaterThan(String value) {
            addCriterion("litigation >", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationGreaterThanOrEqualTo(String value) {
            addCriterion("litigation >=", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationLessThan(String value) {
            addCriterion("litigation <", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationLessThanOrEqualTo(String value) {
            addCriterion("litigation <=", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationLike(String value) {
            addCriterion("litigation like", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationNotLike(String value) {
            addCriterion("litigation not like", value, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationIn(List<String> values) {
            addCriterion("litigation in", values, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationNotIn(List<String> values) {
            addCriterion("litigation not in", values, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationBetween(String value1, String value2) {
            addCriterion("litigation between", value1, value2, "litigation");
            return (Criteria) this;
        }

        public Criteria andLitigationNotBetween(String value1, String value2) {
            addCriterion("litigation not between", value1, value2, "litigation");
            return (Criteria) this;
        }

        public Criteria andCreReportIsNull() {
            addCriterion("cre_report is null");
            return (Criteria) this;
        }

        public Criteria andCreReportIsNotNull() {
            addCriterion("cre_report is not null");
            return (Criteria) this;
        }

        public Criteria andCreReportEqualTo(String value) {
            addCriterion("cre_report =", value, "creReport");
            return (Criteria) this;
        }

        public Criteria andCreReportNotEqualTo(String value) {
            addCriterion("cre_report <>", value, "creReport");
            return (Criteria) this;
        }

        public Criteria andCreReportGreaterThan(String value) {
            addCriterion("cre_report >", value, "creReport");
            return (Criteria) this;
        }

        public Criteria andCreReportGreaterThanOrEqualTo(String value) {
            addCriterion("cre_report >=", value, "creReport");
            return (Criteria) this;
        }

        public Criteria andCreReportLessThan(String value) {
            addCriterion("cre_report <", value, "creReport");
            return (Criteria) this;
        }

        public Criteria andCreReportLessThanOrEqualTo(String value) {
            addCriterion("cre_report <=", value, "creReport");
            return (Criteria) this;
        }

        public Criteria andCreReportLike(String value) {
            addCriterion("cre_report like", value, "creReport");
            return (Criteria) this;
        }

        public Criteria andCreReportNotLike(String value) {
            addCriterion("cre_report not like", value, "creReport");
            return (Criteria) this;
        }

        public Criteria andCreReportIn(List<String> values) {
            addCriterion("cre_report in", values, "creReport");
            return (Criteria) this;
        }

        public Criteria andCreReportNotIn(List<String> values) {
            addCriterion("cre_report not in", values, "creReport");
            return (Criteria) this;
        }

        public Criteria andCreReportBetween(String value1, String value2) {
            addCriterion("cre_report between", value1, value2, "creReport");
            return (Criteria) this;
        }

        public Criteria andCreReportNotBetween(String value1, String value2) {
            addCriterion("cre_report not between", value1, value2, "creReport");
            return (Criteria) this;
        }

        public Criteria andCreditIsNull() {
            addCriterion("credit is null");
            return (Criteria) this;
        }

        public Criteria andCreditIsNotNull() {
            addCriterion("credit is not null");
            return (Criteria) this;
        }

        public Criteria andCreditEqualTo(Integer value) {
            addCriterion("credit =", value, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditNotEqualTo(Integer value) {
            addCriterion("credit <>", value, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditGreaterThan(Integer value) {
            addCriterion("credit >", value, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditGreaterThanOrEqualTo(Integer value) {
            addCriterion("credit >=", value, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditLessThan(Integer value) {
            addCriterion("credit <", value, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditLessThanOrEqualTo(Integer value) {
            addCriterion("credit <=", value, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditIn(List<Integer> values) {
            addCriterion("credit in", values, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditNotIn(List<Integer> values) {
            addCriterion("credit not in", values, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditBetween(Integer value1, Integer value2) {
            addCriterion("credit between", value1, value2, "credit");
            return (Criteria) this;
        }

        public Criteria andCreditNotBetween(Integer value1, Integer value2) {
            addCriterion("credit not between", value1, value2, "credit");
            return (Criteria) this;
        }

        public Criteria andStaffIsNull() {
            addCriterion("staff is null");
            return (Criteria) this;
        }

        public Criteria andStaffIsNotNull() {
            addCriterion("staff is not null");
            return (Criteria) this;
        }

        public Criteria andStaffEqualTo(Integer value) {
            addCriterion("staff =", value, "staff");
            return (Criteria) this;
        }

        public Criteria andStaffNotEqualTo(Integer value) {
            addCriterion("staff <>", value, "staff");
            return (Criteria) this;
        }

        public Criteria andStaffGreaterThan(Integer value) {
            addCriterion("staff >", value, "staff");
            return (Criteria) this;
        }

        public Criteria andStaffGreaterThanOrEqualTo(Integer value) {
            addCriterion("staff >=", value, "staff");
            return (Criteria) this;
        }

        public Criteria andStaffLessThan(Integer value) {
            addCriterion("staff <", value, "staff");
            return (Criteria) this;
        }

        public Criteria andStaffLessThanOrEqualTo(Integer value) {
            addCriterion("staff <=", value, "staff");
            return (Criteria) this;
        }

        public Criteria andStaffIn(List<Integer> values) {
            addCriterion("staff in", values, "staff");
            return (Criteria) this;
        }

        public Criteria andStaffNotIn(List<Integer> values) {
            addCriterion("staff not in", values, "staff");
            return (Criteria) this;
        }

        public Criteria andStaffBetween(Integer value1, Integer value2) {
            addCriterion("staff between", value1, value2, "staff");
            return (Criteria) this;
        }

        public Criteria andStaffNotBetween(Integer value1, Integer value2) {
            addCriterion("staff not between", value1, value2, "staff");
            return (Criteria) this;
        }

        public Criteria andComRegTimeIsNull() {
            addCriterion("com_reg_time is null");
            return (Criteria) this;
        }

        public Criteria andComRegTimeIsNotNull() {
            addCriterion("com_reg_time is not null");
            return (Criteria) this;
        }

        public Criteria andComRegTimeEqualTo(String value) {
            addCriterion("com_reg_time =", value, "comRegTime");
            return (Criteria) this;
        }

        public Criteria andComRegTimeNotEqualTo(String value) {
            addCriterion("com_reg_time <>", value, "comRegTime");
            return (Criteria) this;
        }

        public Criteria andComRegTimeGreaterThan(String value) {
            addCriterion("com_reg_time >", value, "comRegTime");
            return (Criteria) this;
        }

        public Criteria andComRegTimeGreaterThanOrEqualTo(String value) {
            addCriterion("com_reg_time >=", value, "comRegTime");
            return (Criteria) this;
        }

        public Criteria andComRegTimeLessThan(String value) {
            addCriterion("com_reg_time <", value, "comRegTime");
            return (Criteria) this;
        }

        public Criteria andComRegTimeLessThanOrEqualTo(String value) {
            addCriterion("com_reg_time <=", value, "comRegTime");
            return (Criteria) this;
        }

        public Criteria andComRegTimeLike(String value) {
            addCriterion("com_reg_time like", value, "comRegTime");
            return (Criteria) this;
        }

        public Criteria andComRegTimeNotLike(String value) {
            addCriterion("com_reg_time not like", value, "comRegTime");
            return (Criteria) this;
        }

        public Criteria andComRegTimeIn(List<String> values) {
            addCriterion("com_reg_time in", values, "comRegTime");
            return (Criteria) this;
        }

        public Criteria andComRegTimeNotIn(List<String> values) {
            addCriterion("com_reg_time not in", values, "comRegTime");
            return (Criteria) this;
        }

        public Criteria andComRegTimeBetween(String value1, String value2) {
            addCriterion("com_reg_time between", value1, value2, "comRegTime");
            return (Criteria) this;
        }

        public Criteria andComRegTimeNotBetween(String value1, String value2) {
            addCriterion("com_reg_time not between", value1, value2, "comRegTime");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIsNull() {
            addCriterion("legal_person is null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIsNotNull() {
            addCriterion("legal_person is not null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonEqualTo(String value) {
            addCriterion("legal_person =", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotEqualTo(String value) {
            addCriterion("legal_person <>", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonGreaterThan(String value) {
            addCriterion("legal_person >", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonGreaterThanOrEqualTo(String value) {
            addCriterion("legal_person >=", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLessThan(String value) {
            addCriterion("legal_person <", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLessThanOrEqualTo(String value) {
            addCriterion("legal_person <=", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLike(String value) {
            addCriterion("legal_person like", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotLike(String value) {
            addCriterion("legal_person not like", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIn(List<String> values) {
            addCriterion("legal_person in", values, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotIn(List<String> values) {
            addCriterion("legal_person not in", values, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonBetween(String value1, String value2) {
            addCriterion("legal_person between", value1, value2, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotBetween(String value1, String value2) {
            addCriterion("legal_person not between", value1, value2, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeIsNull() {
            addCriterion("social_credit_code is null");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeIsNotNull() {
            addCriterion("social_credit_code is not null");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeEqualTo(String value) {
            addCriterion("social_credit_code =", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeNotEqualTo(String value) {
            addCriterion("social_credit_code <>", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeGreaterThan(String value) {
            addCriterion("social_credit_code >", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeGreaterThanOrEqualTo(String value) {
            addCriterion("social_credit_code >=", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeLessThan(String value) {
            addCriterion("social_credit_code <", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeLessThanOrEqualTo(String value) {
            addCriterion("social_credit_code <=", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeLike(String value) {
            addCriterion("social_credit_code like", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeNotLike(String value) {
            addCriterion("social_credit_code not like", value, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeIn(List<String> values) {
            addCriterion("social_credit_code in", values, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeNotIn(List<String> values) {
            addCriterion("social_credit_code not in", values, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeBetween(String value1, String value2) {
            addCriterion("social_credit_code between", value1, value2, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andSocialCreditCodeNotBetween(String value1, String value2) {
            addCriterion("social_credit_code not between", value1, value2, "socialCreditCode");
            return (Criteria) this;
        }

        public Criteria andRegistCodeIsNull() {
            addCriterion("regist_code is null");
            return (Criteria) this;
        }

        public Criteria andRegistCodeIsNotNull() {
            addCriterion("regist_code is not null");
            return (Criteria) this;
        }

        public Criteria andRegistCodeEqualTo(String value) {
            addCriterion("regist_code =", value, "registCode");
            return (Criteria) this;
        }

        public Criteria andRegistCodeNotEqualTo(String value) {
            addCriterion("regist_code <>", value, "registCode");
            return (Criteria) this;
        }

        public Criteria andRegistCodeGreaterThan(String value) {
            addCriterion("regist_code >", value, "registCode");
            return (Criteria) this;
        }

        public Criteria andRegistCodeGreaterThanOrEqualTo(String value) {
            addCriterion("regist_code >=", value, "registCode");
            return (Criteria) this;
        }

        public Criteria andRegistCodeLessThan(String value) {
            addCriterion("regist_code <", value, "registCode");
            return (Criteria) this;
        }

        public Criteria andRegistCodeLessThanOrEqualTo(String value) {
            addCriterion("regist_code <=", value, "registCode");
            return (Criteria) this;
        }

        public Criteria andRegistCodeLike(String value) {
            addCriterion("regist_code like", value, "registCode");
            return (Criteria) this;
        }

        public Criteria andRegistCodeNotLike(String value) {
            addCriterion("regist_code not like", value, "registCode");
            return (Criteria) this;
        }

        public Criteria andRegistCodeIn(List<String> values) {
            addCriterion("regist_code in", values, "registCode");
            return (Criteria) this;
        }

        public Criteria andRegistCodeNotIn(List<String> values) {
            addCriterion("regist_code not in", values, "registCode");
            return (Criteria) this;
        }

        public Criteria andRegistCodeBetween(String value1, String value2) {
            addCriterion("regist_code between", value1, value2, "registCode");
            return (Criteria) this;
        }

        public Criteria andRegistCodeNotBetween(String value1, String value2) {
            addCriterion("regist_code not between", value1, value2, "registCode");
            return (Criteria) this;
        }

        public Criteria andMainBusinessIsNull() {
            addCriterion("main_business is null");
            return (Criteria) this;
        }

        public Criteria andMainBusinessIsNotNull() {
            addCriterion("main_business is not null");
            return (Criteria) this;
        }

        public Criteria andMainBusinessEqualTo(String value) {
            addCriterion("main_business =", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotEqualTo(String value) {
            addCriterion("main_business <>", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessGreaterThan(String value) {
            addCriterion("main_business >", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessGreaterThanOrEqualTo(String value) {
            addCriterion("main_business >=", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessLessThan(String value) {
            addCriterion("main_business <", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessLessThanOrEqualTo(String value) {
            addCriterion("main_business <=", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessLike(String value) {
            addCriterion("main_business like", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotLike(String value) {
            addCriterion("main_business not like", value, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessIn(List<String> values) {
            addCriterion("main_business in", values, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotIn(List<String> values) {
            addCriterion("main_business not in", values, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessBetween(String value1, String value2) {
            addCriterion("main_business between", value1, value2, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andMainBusinessNotBetween(String value1, String value2) {
            addCriterion("main_business not between", value1, value2, "mainBusiness");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesIsNull() {
            addCriterion("overdue_times is null");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesIsNotNull() {
            addCriterion("overdue_times is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesEqualTo(String value) {
            addCriterion("overdue_times =", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesNotEqualTo(String value) {
            addCriterion("overdue_times <>", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesGreaterThan(String value) {
            addCriterion("overdue_times >", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_times >=", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesLessThan(String value) {
            addCriterion("overdue_times <", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesLessThanOrEqualTo(String value) {
            addCriterion("overdue_times <=", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesLike(String value) {
            addCriterion("overdue_times like", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesNotLike(String value) {
            addCriterion("overdue_times not like", value, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesIn(List<String> values) {
            addCriterion("overdue_times in", values, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesNotIn(List<String> values) {
            addCriterion("overdue_times not in", values, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesBetween(String value1, String value2) {
            addCriterion("overdue_times between", value1, value2, "overdueTimes");
            return (Criteria) this;
        }

        public Criteria andOverdueTimesNotBetween(String value1, String value2) {
            addCriterion("overdue_times not between", value1, value2, "overdueTimes");
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

        public Criteria andOverdueAmountEqualTo(String value) {
            addCriterion("overdue_amount =", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountNotEqualTo(String value) {
            addCriterion("overdue_amount <>", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountGreaterThan(String value) {
            addCriterion("overdue_amount >", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_amount >=", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountLessThan(String value) {
            addCriterion("overdue_amount <", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountLessThanOrEqualTo(String value) {
            addCriterion("overdue_amount <=", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountLike(String value) {
            addCriterion("overdue_amount like", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountNotLike(String value) {
            addCriterion("overdue_amount not like", value, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountIn(List<String> values) {
            addCriterion("overdue_amount in", values, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountNotIn(List<String> values) {
            addCriterion("overdue_amount not in", values, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountBetween(String value1, String value2) {
            addCriterion("overdue_amount between", value1, value2, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andOverdueAmountNotBetween(String value1, String value2) {
            addCriterion("overdue_amount not between", value1, value2, "overdueAmount");
            return (Criteria) this;
        }

        public Criteria andIsCertificateIsNull() {
            addCriterion("is_certificate is null");
            return (Criteria) this;
        }

        public Criteria andIsCertificateIsNotNull() {
            addCriterion("is_certificate is not null");
            return (Criteria) this;
        }

        public Criteria andIsCertificateEqualTo(Integer value) {
            addCriterion("is_certificate =", value, "isCertificate");
            return (Criteria) this;
        }

        public Criteria andIsCertificateNotEqualTo(Integer value) {
            addCriterion("is_certificate <>", value, "isCertificate");
            return (Criteria) this;
        }

        public Criteria andIsCertificateGreaterThan(Integer value) {
            addCriterion("is_certificate >", value, "isCertificate");
            return (Criteria) this;
        }

        public Criteria andIsCertificateGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_certificate >=", value, "isCertificate");
            return (Criteria) this;
        }

        public Criteria andIsCertificateLessThan(Integer value) {
            addCriterion("is_certificate <", value, "isCertificate");
            return (Criteria) this;
        }

        public Criteria andIsCertificateLessThanOrEqualTo(Integer value) {
            addCriterion("is_certificate <=", value, "isCertificate");
            return (Criteria) this;
        }

        public Criteria andIsCertificateIn(List<Integer> values) {
            addCriterion("is_certificate in", values, "isCertificate");
            return (Criteria) this;
        }

        public Criteria andIsCertificateNotIn(List<Integer> values) {
            addCriterion("is_certificate not in", values, "isCertificate");
            return (Criteria) this;
        }

        public Criteria andIsCertificateBetween(Integer value1, Integer value2) {
            addCriterion("is_certificate between", value1, value2, "isCertificate");
            return (Criteria) this;
        }

        public Criteria andIsCertificateNotBetween(Integer value1, Integer value2) {
            addCriterion("is_certificate not between", value1, value2, "isCertificate");
            return (Criteria) this;
        }

        public Criteria andIsOperationIsNull() {
            addCriterion("is_operation is null");
            return (Criteria) this;
        }

        public Criteria andIsOperationIsNotNull() {
            addCriterion("is_operation is not null");
            return (Criteria) this;
        }

        public Criteria andIsOperationEqualTo(Integer value) {
            addCriterion("is_operation =", value, "isOperation");
            return (Criteria) this;
        }

        public Criteria andIsOperationNotEqualTo(Integer value) {
            addCriterion("is_operation <>", value, "isOperation");
            return (Criteria) this;
        }

        public Criteria andIsOperationGreaterThan(Integer value) {
            addCriterion("is_operation >", value, "isOperation");
            return (Criteria) this;
        }

        public Criteria andIsOperationGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_operation >=", value, "isOperation");
            return (Criteria) this;
        }

        public Criteria andIsOperationLessThan(Integer value) {
            addCriterion("is_operation <", value, "isOperation");
            return (Criteria) this;
        }

        public Criteria andIsOperationLessThanOrEqualTo(Integer value) {
            addCriterion("is_operation <=", value, "isOperation");
            return (Criteria) this;
        }

        public Criteria andIsOperationIn(List<Integer> values) {
            addCriterion("is_operation in", values, "isOperation");
            return (Criteria) this;
        }

        public Criteria andIsOperationNotIn(List<Integer> values) {
            addCriterion("is_operation not in", values, "isOperation");
            return (Criteria) this;
        }

        public Criteria andIsOperationBetween(Integer value1, Integer value2) {
            addCriterion("is_operation between", value1, value2, "isOperation");
            return (Criteria) this;
        }

        public Criteria andIsOperationNotBetween(Integer value1, Integer value2) {
            addCriterion("is_operation not between", value1, value2, "isOperation");
            return (Criteria) this;
        }

        public Criteria andIsFinanceIsNull() {
            addCriterion("is_finance is null");
            return (Criteria) this;
        }

        public Criteria andIsFinanceIsNotNull() {
            addCriterion("is_finance is not null");
            return (Criteria) this;
        }

        public Criteria andIsFinanceEqualTo(Integer value) {
            addCriterion("is_finance =", value, "isFinance");
            return (Criteria) this;
        }

        public Criteria andIsFinanceNotEqualTo(Integer value) {
            addCriterion("is_finance <>", value, "isFinance");
            return (Criteria) this;
        }

        public Criteria andIsFinanceGreaterThan(Integer value) {
            addCriterion("is_finance >", value, "isFinance");
            return (Criteria) this;
        }

        public Criteria andIsFinanceGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_finance >=", value, "isFinance");
            return (Criteria) this;
        }

        public Criteria andIsFinanceLessThan(Integer value) {
            addCriterion("is_finance <", value, "isFinance");
            return (Criteria) this;
        }

        public Criteria andIsFinanceLessThanOrEqualTo(Integer value) {
            addCriterion("is_finance <=", value, "isFinance");
            return (Criteria) this;
        }

        public Criteria andIsFinanceIn(List<Integer> values) {
            addCriterion("is_finance in", values, "isFinance");
            return (Criteria) this;
        }

        public Criteria andIsFinanceNotIn(List<Integer> values) {
            addCriterion("is_finance not in", values, "isFinance");
            return (Criteria) this;
        }

        public Criteria andIsFinanceBetween(Integer value1, Integer value2) {
            addCriterion("is_finance between", value1, value2, "isFinance");
            return (Criteria) this;
        }

        public Criteria andIsFinanceNotBetween(Integer value1, Integer value2) {
            addCriterion("is_finance not between", value1, value2, "isFinance");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseCreidtIsNull() {
            addCriterion("is_enterprise_creidt is null");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseCreidtIsNotNull() {
            addCriterion("is_enterprise_creidt is not null");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseCreidtEqualTo(Integer value) {
            addCriterion("is_enterprise_creidt =", value, "isEnterpriseCreidt");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseCreidtNotEqualTo(Integer value) {
            addCriterion("is_enterprise_creidt <>", value, "isEnterpriseCreidt");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseCreidtGreaterThan(Integer value) {
            addCriterion("is_enterprise_creidt >", value, "isEnterpriseCreidt");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseCreidtGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_enterprise_creidt >=", value, "isEnterpriseCreidt");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseCreidtLessThan(Integer value) {
            addCriterion("is_enterprise_creidt <", value, "isEnterpriseCreidt");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseCreidtLessThanOrEqualTo(Integer value) {
            addCriterion("is_enterprise_creidt <=", value, "isEnterpriseCreidt");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseCreidtIn(List<Integer> values) {
            addCriterion("is_enterprise_creidt in", values, "isEnterpriseCreidt");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseCreidtNotIn(List<Integer> values) {
            addCriterion("is_enterprise_creidt not in", values, "isEnterpriseCreidt");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseCreidtBetween(Integer value1, Integer value2) {
            addCriterion("is_enterprise_creidt between", value1, value2, "isEnterpriseCreidt");
            return (Criteria) this;
        }

        public Criteria andIsEnterpriseCreidtNotBetween(Integer value1, Integer value2) {
            addCriterion("is_enterprise_creidt not between", value1, value2, "isEnterpriseCreidt");
            return (Criteria) this;
        }

        public Criteria andIsLegalPersonIsNull() {
            addCriterion("is_legal_person is null");
            return (Criteria) this;
        }

        public Criteria andIsLegalPersonIsNotNull() {
            addCriterion("is_legal_person is not null");
            return (Criteria) this;
        }

        public Criteria andIsLegalPersonEqualTo(Integer value) {
            addCriterion("is_legal_person =", value, "isLegalPerson");
            return (Criteria) this;
        }

        public Criteria andIsLegalPersonNotEqualTo(Integer value) {
            addCriterion("is_legal_person <>", value, "isLegalPerson");
            return (Criteria) this;
        }

        public Criteria andIsLegalPersonGreaterThan(Integer value) {
            addCriterion("is_legal_person >", value, "isLegalPerson");
            return (Criteria) this;
        }

        public Criteria andIsLegalPersonGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_legal_person >=", value, "isLegalPerson");
            return (Criteria) this;
        }

        public Criteria andIsLegalPersonLessThan(Integer value) {
            addCriterion("is_legal_person <", value, "isLegalPerson");
            return (Criteria) this;
        }

        public Criteria andIsLegalPersonLessThanOrEqualTo(Integer value) {
            addCriterion("is_legal_person <=", value, "isLegalPerson");
            return (Criteria) this;
        }

        public Criteria andIsLegalPersonIn(List<Integer> values) {
            addCriterion("is_legal_person in", values, "isLegalPerson");
            return (Criteria) this;
        }

        public Criteria andIsLegalPersonNotIn(List<Integer> values) {
            addCriterion("is_legal_person not in", values, "isLegalPerson");
            return (Criteria) this;
        }

        public Criteria andIsLegalPersonBetween(Integer value1, Integer value2) {
            addCriterion("is_legal_person between", value1, value2, "isLegalPerson");
            return (Criteria) this;
        }

        public Criteria andIsLegalPersonNotBetween(Integer value1, Integer value2) {
            addCriterion("is_legal_person not between", value1, value2, "isLegalPerson");
            return (Criteria) this;
        }

        public Criteria andIsAssetIsNull() {
            addCriterion("is_asset is null");
            return (Criteria) this;
        }

        public Criteria andIsAssetIsNotNull() {
            addCriterion("is_asset is not null");
            return (Criteria) this;
        }

        public Criteria andIsAssetEqualTo(Integer value) {
            addCriterion("is_asset =", value, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetNotEqualTo(Integer value) {
            addCriterion("is_asset <>", value, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetGreaterThan(Integer value) {
            addCriterion("is_asset >", value, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_asset >=", value, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetLessThan(Integer value) {
            addCriterion("is_asset <", value, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetLessThanOrEqualTo(Integer value) {
            addCriterion("is_asset <=", value, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetIn(List<Integer> values) {
            addCriterion("is_asset in", values, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetNotIn(List<Integer> values) {
            addCriterion("is_asset not in", values, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetBetween(Integer value1, Integer value2) {
            addCriterion("is_asset between", value1, value2, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsAssetNotBetween(Integer value1, Integer value2) {
            addCriterion("is_asset not between", value1, value2, "isAsset");
            return (Criteria) this;
        }

        public Criteria andIsPurchaseContractIsNull() {
            addCriterion("is_purchase_contract is null");
            return (Criteria) this;
        }

        public Criteria andIsPurchaseContractIsNotNull() {
            addCriterion("is_purchase_contract is not null");
            return (Criteria) this;
        }

        public Criteria andIsPurchaseContractEqualTo(Integer value) {
            addCriterion("is_purchase_contract =", value, "isPurchaseContract");
            return (Criteria) this;
        }

        public Criteria andIsPurchaseContractNotEqualTo(Integer value) {
            addCriterion("is_purchase_contract <>", value, "isPurchaseContract");
            return (Criteria) this;
        }

        public Criteria andIsPurchaseContractGreaterThan(Integer value) {
            addCriterion("is_purchase_contract >", value, "isPurchaseContract");
            return (Criteria) this;
        }

        public Criteria andIsPurchaseContractGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_purchase_contract >=", value, "isPurchaseContract");
            return (Criteria) this;
        }

        public Criteria andIsPurchaseContractLessThan(Integer value) {
            addCriterion("is_purchase_contract <", value, "isPurchaseContract");
            return (Criteria) this;
        }

        public Criteria andIsPurchaseContractLessThanOrEqualTo(Integer value) {
            addCriterion("is_purchase_contract <=", value, "isPurchaseContract");
            return (Criteria) this;
        }

        public Criteria andIsPurchaseContractIn(List<Integer> values) {
            addCriterion("is_purchase_contract in", values, "isPurchaseContract");
            return (Criteria) this;
        }

        public Criteria andIsPurchaseContractNotIn(List<Integer> values) {
            addCriterion("is_purchase_contract not in", values, "isPurchaseContract");
            return (Criteria) this;
        }

        public Criteria andIsPurchaseContractBetween(Integer value1, Integer value2) {
            addCriterion("is_purchase_contract between", value1, value2, "isPurchaseContract");
            return (Criteria) this;
        }

        public Criteria andIsPurchaseContractNotBetween(Integer value1, Integer value2) {
            addCriterion("is_purchase_contract not between", value1, value2, "isPurchaseContract");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractIsNull() {
            addCriterion("is_supply_contract is null");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractIsNotNull() {
            addCriterion("is_supply_contract is not null");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractEqualTo(String value) {
            addCriterion("is_supply_contract =", value, "isSupplyContract");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractNotEqualTo(String value) {
            addCriterion("is_supply_contract <>", value, "isSupplyContract");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractGreaterThan(String value) {
            addCriterion("is_supply_contract >", value, "isSupplyContract");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractGreaterThanOrEqualTo(String value) {
            addCriterion("is_supply_contract >=", value, "isSupplyContract");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractLessThan(String value) {
            addCriterion("is_supply_contract <", value, "isSupplyContract");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractLessThanOrEqualTo(String value) {
            addCriterion("is_supply_contract <=", value, "isSupplyContract");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractLike(String value) {
            addCriterion("is_supply_contract like", value, "isSupplyContract");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractNotLike(String value) {
            addCriterion("is_supply_contract not like", value, "isSupplyContract");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractIn(List<String> values) {
            addCriterion("is_supply_contract in", values, "isSupplyContract");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractNotIn(List<String> values) {
            addCriterion("is_supply_contract not in", values, "isSupplyContract");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractBetween(String value1, String value2) {
            addCriterion("is_supply_contract between", value1, value2, "isSupplyContract");
            return (Criteria) this;
        }

        public Criteria andIsSupplyContractNotBetween(String value1, String value2) {
            addCriterion("is_supply_contract not between", value1, value2, "isSupplyContract");
            return (Criteria) this;
        }

        public Criteria andOverdueReportIsNull() {
            addCriterion("overdue_report is null");
            return (Criteria) this;
        }

        public Criteria andOverdueReportIsNotNull() {
            addCriterion("overdue_report is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueReportEqualTo(String value) {
            addCriterion("overdue_report =", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportNotEqualTo(String value) {
            addCriterion("overdue_report <>", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportGreaterThan(String value) {
            addCriterion("overdue_report >", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportGreaterThanOrEqualTo(String value) {
            addCriterion("overdue_report >=", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportLessThan(String value) {
            addCriterion("overdue_report <", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportLessThanOrEqualTo(String value) {
            addCriterion("overdue_report <=", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportLike(String value) {
            addCriterion("overdue_report like", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportNotLike(String value) {
            addCriterion("overdue_report not like", value, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportIn(List<String> values) {
            addCriterion("overdue_report in", values, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportNotIn(List<String> values) {
            addCriterion("overdue_report not in", values, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportBetween(String value1, String value2) {
            addCriterion("overdue_report between", value1, value2, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andOverdueReportNotBetween(String value1, String value2) {
            addCriterion("overdue_report not between", value1, value2, "overdueReport");
            return (Criteria) this;
        }

        public Criteria andDebtSituationIsNull() {
            addCriterion("debt_situation is null");
            return (Criteria) this;
        }

        public Criteria andDebtSituationIsNotNull() {
            addCriterion("debt_situation is not null");
            return (Criteria) this;
        }

        public Criteria andDebtSituationEqualTo(String value) {
            addCriterion("debt_situation =", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationNotEqualTo(String value) {
            addCriterion("debt_situation <>", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationGreaterThan(String value) {
            addCriterion("debt_situation >", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationGreaterThanOrEqualTo(String value) {
            addCriterion("debt_situation >=", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationLessThan(String value) {
            addCriterion("debt_situation <", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationLessThanOrEqualTo(String value) {
            addCriterion("debt_situation <=", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationLike(String value) {
            addCriterion("debt_situation like", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationNotLike(String value) {
            addCriterion("debt_situation not like", value, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationIn(List<String> values) {
            addCriterion("debt_situation in", values, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationNotIn(List<String> values) {
            addCriterion("debt_situation not in", values, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationBetween(String value1, String value2) {
            addCriterion("debt_situation between", value1, value2, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andDebtSituationNotBetween(String value1, String value2) {
            addCriterion("debt_situation not between", value1, value2, "debtSituation");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedIsNull() {
            addCriterion("other_borrowed is null");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedIsNotNull() {
            addCriterion("other_borrowed is not null");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedEqualTo(String value) {
            addCriterion("other_borrowed =", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedNotEqualTo(String value) {
            addCriterion("other_borrowed <>", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedGreaterThan(String value) {
            addCriterion("other_borrowed >", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedGreaterThanOrEqualTo(String value) {
            addCriterion("other_borrowed >=", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedLessThan(String value) {
            addCriterion("other_borrowed <", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedLessThanOrEqualTo(String value) {
            addCriterion("other_borrowed <=", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedLike(String value) {
            addCriterion("other_borrowed like", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedNotLike(String value) {
            addCriterion("other_borrowed not like", value, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedIn(List<String> values) {
            addCriterion("other_borrowed in", values, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedNotIn(List<String> values) {
            addCriterion("other_borrowed not in", values, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedBetween(String value1, String value2) {
            addCriterion("other_borrowed between", value1, value2, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andOtherBorrowedNotBetween(String value1, String value2) {
            addCriterion("other_borrowed not between", value1, value2, "otherBorrowed");
            return (Criteria) this;
        }

        public Criteria andIsFundsIsNull() {
            addCriterion("is_funds is null");
            return (Criteria) this;
        }

        public Criteria andIsFundsIsNotNull() {
            addCriterion("is_funds is not null");
            return (Criteria) this;
        }

        public Criteria andIsFundsEqualTo(String value) {
            addCriterion("is_funds =", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsNotEqualTo(String value) {
            addCriterion("is_funds <>", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsGreaterThan(String value) {
            addCriterion("is_funds >", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsGreaterThanOrEqualTo(String value) {
            addCriterion("is_funds >=", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsLessThan(String value) {
            addCriterion("is_funds <", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsLessThanOrEqualTo(String value) {
            addCriterion("is_funds <=", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsLike(String value) {
            addCriterion("is_funds like", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsNotLike(String value) {
            addCriterion("is_funds not like", value, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsIn(List<String> values) {
            addCriterion("is_funds in", values, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsNotIn(List<String> values) {
            addCriterion("is_funds not in", values, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsBetween(String value1, String value2) {
            addCriterion("is_funds between", value1, value2, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsFundsNotBetween(String value1, String value2) {
            addCriterion("is_funds not between", value1, value2, "isFunds");
            return (Criteria) this;
        }

        public Criteria andIsManagedIsNull() {
            addCriterion("is_managed is null");
            return (Criteria) this;
        }

        public Criteria andIsManagedIsNotNull() {
            addCriterion("is_managed is not null");
            return (Criteria) this;
        }

        public Criteria andIsManagedEqualTo(String value) {
            addCriterion("is_managed =", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedNotEqualTo(String value) {
            addCriterion("is_managed <>", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedGreaterThan(String value) {
            addCriterion("is_managed >", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedGreaterThanOrEqualTo(String value) {
            addCriterion("is_managed >=", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedLessThan(String value) {
            addCriterion("is_managed <", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedLessThanOrEqualTo(String value) {
            addCriterion("is_managed <=", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedLike(String value) {
            addCriterion("is_managed like", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedNotLike(String value) {
            addCriterion("is_managed not like", value, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedIn(List<String> values) {
            addCriterion("is_managed in", values, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedNotIn(List<String> values) {
            addCriterion("is_managed not in", values, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedBetween(String value1, String value2) {
            addCriterion("is_managed between", value1, value2, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsManagedNotBetween(String value1, String value2) {
            addCriterion("is_managed not between", value1, value2, "isManaged");
            return (Criteria) this;
        }

        public Criteria andIsAbilityIsNull() {
            addCriterion("is_ability is null");
            return (Criteria) this;
        }

        public Criteria andIsAbilityIsNotNull() {
            addCriterion("is_ability is not null");
            return (Criteria) this;
        }

        public Criteria andIsAbilityEqualTo(String value) {
            addCriterion("is_ability =", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityNotEqualTo(String value) {
            addCriterion("is_ability <>", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityGreaterThan(String value) {
            addCriterion("is_ability >", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityGreaterThanOrEqualTo(String value) {
            addCriterion("is_ability >=", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityLessThan(String value) {
            addCriterion("is_ability <", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityLessThanOrEqualTo(String value) {
            addCriterion("is_ability <=", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityLike(String value) {
            addCriterion("is_ability like", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityNotLike(String value) {
            addCriterion("is_ability not like", value, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityIn(List<String> values) {
            addCriterion("is_ability in", values, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityNotIn(List<String> values) {
            addCriterion("is_ability not in", values, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityBetween(String value1, String value2) {
            addCriterion("is_ability between", value1, value2, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsAbilityNotBetween(String value1, String value2) {
            addCriterion("is_ability not between", value1, value2, "isAbility");
            return (Criteria) this;
        }

        public Criteria andIsOverdueIsNull() {
            addCriterion("is_overdue is null");
            return (Criteria) this;
        }

        public Criteria andIsOverdueIsNotNull() {
            addCriterion("is_overdue is not null");
            return (Criteria) this;
        }

        public Criteria andIsOverdueEqualTo(String value) {
            addCriterion("is_overdue =", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueNotEqualTo(String value) {
            addCriterion("is_overdue <>", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueGreaterThan(String value) {
            addCriterion("is_overdue >", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueGreaterThanOrEqualTo(String value) {
            addCriterion("is_overdue >=", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueLessThan(String value) {
            addCriterion("is_overdue <", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueLessThanOrEqualTo(String value) {
            addCriterion("is_overdue <=", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueLike(String value) {
            addCriterion("is_overdue like", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueNotLike(String value) {
            addCriterion("is_overdue not like", value, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueIn(List<String> values) {
            addCriterion("is_overdue in", values, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueNotIn(List<String> values) {
            addCriterion("is_overdue not in", values, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueBetween(String value1, String value2) {
            addCriterion("is_overdue between", value1, value2, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsOverdueNotBetween(String value1, String value2) {
            addCriterion("is_overdue not between", value1, value2, "isOverdue");
            return (Criteria) this;
        }

        public Criteria andIsComplaintIsNull() {
            addCriterion("is_complaint is null");
            return (Criteria) this;
        }

        public Criteria andIsComplaintIsNotNull() {
            addCriterion("is_complaint is not null");
            return (Criteria) this;
        }

        public Criteria andIsComplaintEqualTo(String value) {
            addCriterion("is_complaint =", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintNotEqualTo(String value) {
            addCriterion("is_complaint <>", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintGreaterThan(String value) {
            addCriterion("is_complaint >", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintGreaterThanOrEqualTo(String value) {
            addCriterion("is_complaint >=", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintLessThan(String value) {
            addCriterion("is_complaint <", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintLessThanOrEqualTo(String value) {
            addCriterion("is_complaint <=", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintLike(String value) {
            addCriterion("is_complaint like", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintNotLike(String value) {
            addCriterion("is_complaint not like", value, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintIn(List<String> values) {
            addCriterion("is_complaint in", values, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintNotIn(List<String> values) {
            addCriterion("is_complaint not in", values, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintBetween(String value1, String value2) {
            addCriterion("is_complaint between", value1, value2, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsComplaintNotBetween(String value1, String value2) {
            addCriterion("is_complaint not between", value1, value2, "isComplaint");
            return (Criteria) this;
        }

        public Criteria andIsPunishedIsNull() {
            addCriterion("is_punished is null");
            return (Criteria) this;
        }

        public Criteria andIsPunishedIsNotNull() {
            addCriterion("is_punished is not null");
            return (Criteria) this;
        }

        public Criteria andIsPunishedEqualTo(String value) {
            addCriterion("is_punished =", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedNotEqualTo(String value) {
            addCriterion("is_punished <>", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedGreaterThan(String value) {
            addCriterion("is_punished >", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedGreaterThanOrEqualTo(String value) {
            addCriterion("is_punished >=", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedLessThan(String value) {
            addCriterion("is_punished <", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedLessThanOrEqualTo(String value) {
            addCriterion("is_punished <=", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedLike(String value) {
            addCriterion("is_punished like", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedNotLike(String value) {
            addCriterion("is_punished not like", value, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedIn(List<String> values) {
            addCriterion("is_punished in", values, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedNotIn(List<String> values) {
            addCriterion("is_punished not in", values, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedBetween(String value1, String value2) {
            addCriterion("is_punished between", value1, value2, "isPunished");
            return (Criteria) this;
        }

        public Criteria andIsPunishedNotBetween(String value1, String value2) {
            addCriterion("is_punished not between", value1, value2, "isPunished");
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