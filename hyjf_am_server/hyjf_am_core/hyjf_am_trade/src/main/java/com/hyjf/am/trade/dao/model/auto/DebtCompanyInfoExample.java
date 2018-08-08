package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class DebtCompanyInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtCompanyInfoExample() {
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

        public Criteria andBorrowPreNidIsNull() {
            addCriterion("borrow_pre_nid is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidIsNotNull() {
            addCriterion("borrow_pre_nid is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidEqualTo(Integer value) {
            addCriterion("borrow_pre_nid =", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotEqualTo(Integer value) {
            addCriterion("borrow_pre_nid <>", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidGreaterThan(Integer value) {
            addCriterion("borrow_pre_nid >", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidGreaterThanOrEqualTo(Integer value) {
            addCriterion("borrow_pre_nid >=", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidLessThan(Integer value) {
            addCriterion("borrow_pre_nid <", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidLessThanOrEqualTo(Integer value) {
            addCriterion("borrow_pre_nid <=", value, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidIn(List<Integer> values) {
            addCriterion("borrow_pre_nid in", values, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotIn(List<Integer> values) {
            addCriterion("borrow_pre_nid not in", values, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidBetween(Integer value1, Integer value2) {
            addCriterion("borrow_pre_nid between", value1, value2, "borrowPreNid");
            return (Criteria) this;
        }

        public Criteria andBorrowPreNidNotBetween(Integer value1, Integer value2) {
            addCriterion("borrow_pre_nid not between", value1, value2, "borrowPreNid");
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

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
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

        public Criteria andRegCaptialIsNull() {
            addCriterion("reg_captial is null");
            return (Criteria) this;
        }

        public Criteria andRegCaptialIsNotNull() {
            addCriterion("reg_captial is not null");
            return (Criteria) this;
        }

        public Criteria andRegCaptialEqualTo(Integer value) {
            addCriterion("reg_captial =", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialNotEqualTo(Integer value) {
            addCriterion("reg_captial <>", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialGreaterThan(Integer value) {
            addCriterion("reg_captial >", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialGreaterThanOrEqualTo(Integer value) {
            addCriterion("reg_captial >=", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialLessThan(Integer value) {
            addCriterion("reg_captial <", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialLessThanOrEqualTo(Integer value) {
            addCriterion("reg_captial <=", value, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialIn(List<Integer> values) {
            addCriterion("reg_captial in", values, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialNotIn(List<Integer> values) {
            addCriterion("reg_captial not in", values, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialBetween(Integer value1, Integer value2) {
            addCriterion("reg_captial between", value1, value2, "regCaptial");
            return (Criteria) this;
        }

        public Criteria andRegCaptialNotBetween(Integer value1, Integer value2) {
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