package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class MspTitleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MspTitleExample() {
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

        public Criteria andApplyIdIsNull() {
            addCriterion("apply_id is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(String value) {
            addCriterion("apply_id =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(String value) {
            addCriterion("apply_id <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(String value) {
            addCriterion("apply_id >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(String value) {
            addCriterion("apply_id >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(String value) {
            addCriterion("apply_id <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(String value) {
            addCriterion("apply_id <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLike(String value) {
            addCriterion("apply_id like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotLike(String value) {
            addCriterion("apply_id not like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<String> values) {
            addCriterion("apply_id in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<String> values) {
            addCriterion("apply_id not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(String value1, String value2) {
            addCriterion("apply_id between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(String value1, String value2) {
            addCriterion("apply_id not between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andCustomernameIsNull() {
            addCriterion("customerName is null");
            return (Criteria) this;
        }

        public Criteria andCustomernameIsNotNull() {
            addCriterion("customerName is not null");
            return (Criteria) this;
        }

        public Criteria andCustomernameEqualTo(String value) {
            addCriterion("customerName =", value, "customername");
            return (Criteria) this;
        }

        public Criteria andCustomernameNotEqualTo(String value) {
            addCriterion("customerName <>", value, "customername");
            return (Criteria) this;
        }

        public Criteria andCustomernameGreaterThan(String value) {
            addCriterion("customerName >", value, "customername");
            return (Criteria) this;
        }

        public Criteria andCustomernameGreaterThanOrEqualTo(String value) {
            addCriterion("customerName >=", value, "customername");
            return (Criteria) this;
        }

        public Criteria andCustomernameLessThan(String value) {
            addCriterion("customerName <", value, "customername");
            return (Criteria) this;
        }

        public Criteria andCustomernameLessThanOrEqualTo(String value) {
            addCriterion("customerName <=", value, "customername");
            return (Criteria) this;
        }

        public Criteria andCustomernameLike(String value) {
            addCriterion("customerName like", value, "customername");
            return (Criteria) this;
        }

        public Criteria andCustomernameNotLike(String value) {
            addCriterion("customerName not like", value, "customername");
            return (Criteria) this;
        }

        public Criteria andCustomernameIn(List<String> values) {
            addCriterion("customerName in", values, "customername");
            return (Criteria) this;
        }

        public Criteria andCustomernameNotIn(List<String> values) {
            addCriterion("customerName not in", values, "customername");
            return (Criteria) this;
        }

        public Criteria andCustomernameBetween(String value1, String value2) {
            addCriterion("customerName between", value1, value2, "customername");
            return (Criteria) this;
        }

        public Criteria andCustomernameNotBetween(String value1, String value2) {
            addCriterion("customerName not between", value1, value2, "customername");
            return (Criteria) this;
        }

        public Criteria andPapernumberIsNull() {
            addCriterion("paperNumber is null");
            return (Criteria) this;
        }

        public Criteria andPapernumberIsNotNull() {
            addCriterion("paperNumber is not null");
            return (Criteria) this;
        }

        public Criteria andPapernumberEqualTo(String value) {
            addCriterion("paperNumber =", value, "papernumber");
            return (Criteria) this;
        }

        public Criteria andPapernumberNotEqualTo(String value) {
            addCriterion("paperNumber <>", value, "papernumber");
            return (Criteria) this;
        }

        public Criteria andPapernumberGreaterThan(String value) {
            addCriterion("paperNumber >", value, "papernumber");
            return (Criteria) this;
        }

        public Criteria andPapernumberGreaterThanOrEqualTo(String value) {
            addCriterion("paperNumber >=", value, "papernumber");
            return (Criteria) this;
        }

        public Criteria andPapernumberLessThan(String value) {
            addCriterion("paperNumber <", value, "papernumber");
            return (Criteria) this;
        }

        public Criteria andPapernumberLessThanOrEqualTo(String value) {
            addCriterion("paperNumber <=", value, "papernumber");
            return (Criteria) this;
        }

        public Criteria andPapernumberLike(String value) {
            addCriterion("paperNumber like", value, "papernumber");
            return (Criteria) this;
        }

        public Criteria andPapernumberNotLike(String value) {
            addCriterion("paperNumber not like", value, "papernumber");
            return (Criteria) this;
        }

        public Criteria andPapernumberIn(List<String> values) {
            addCriterion("paperNumber in", values, "papernumber");
            return (Criteria) this;
        }

        public Criteria andPapernumberNotIn(List<String> values) {
            addCriterion("paperNumber not in", values, "papernumber");
            return (Criteria) this;
        }

        public Criteria andPapernumberBetween(String value1, String value2) {
            addCriterion("paperNumber between", value1, value2, "papernumber");
            return (Criteria) this;
        }

        public Criteria andPapernumberNotBetween(String value1, String value2) {
            addCriterion("paperNumber not between", value1, value2, "papernumber");
            return (Criteria) this;
        }

        public Criteria andReporttimeIsNull() {
            addCriterion("reportTime is null");
            return (Criteria) this;
        }

        public Criteria andReporttimeIsNotNull() {
            addCriterion("reportTime is not null");
            return (Criteria) this;
        }

        public Criteria andReporttimeEqualTo(String value) {
            addCriterion("reportTime =", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeNotEqualTo(String value) {
            addCriterion("reportTime <>", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeGreaterThan(String value) {
            addCriterion("reportTime >", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeGreaterThanOrEqualTo(String value) {
            addCriterion("reportTime >=", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeLessThan(String value) {
            addCriterion("reportTime <", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeLessThanOrEqualTo(String value) {
            addCriterion("reportTime <=", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeLike(String value) {
            addCriterion("reportTime like", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeNotLike(String value) {
            addCriterion("reportTime not like", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeIn(List<String> values) {
            addCriterion("reportTime in", values, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeNotIn(List<String> values) {
            addCriterion("reportTime not in", values, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeBetween(String value1, String value2) {
            addCriterion("reportTime between", value1, value2, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeNotBetween(String value1, String value2) {
            addCriterion("reportTime not between", value1, value2, "reporttime");
            return (Criteria) this;
        }

        public Criteria andWjqcountIsNull() {
            addCriterion("wjqCount is null");
            return (Criteria) this;
        }

        public Criteria andWjqcountIsNotNull() {
            addCriterion("wjqCount is not null");
            return (Criteria) this;
        }

        public Criteria andWjqcountEqualTo(String value) {
            addCriterion("wjqCount =", value, "wjqcount");
            return (Criteria) this;
        }

        public Criteria andWjqcountNotEqualTo(String value) {
            addCriterion("wjqCount <>", value, "wjqcount");
            return (Criteria) this;
        }

        public Criteria andWjqcountGreaterThan(String value) {
            addCriterion("wjqCount >", value, "wjqcount");
            return (Criteria) this;
        }

        public Criteria andWjqcountGreaterThanOrEqualTo(String value) {
            addCriterion("wjqCount >=", value, "wjqcount");
            return (Criteria) this;
        }

        public Criteria andWjqcountLessThan(String value) {
            addCriterion("wjqCount <", value, "wjqcount");
            return (Criteria) this;
        }

        public Criteria andWjqcountLessThanOrEqualTo(String value) {
            addCriterion("wjqCount <=", value, "wjqcount");
            return (Criteria) this;
        }

        public Criteria andWjqcountLike(String value) {
            addCriterion("wjqCount like", value, "wjqcount");
            return (Criteria) this;
        }

        public Criteria andWjqcountNotLike(String value) {
            addCriterion("wjqCount not like", value, "wjqcount");
            return (Criteria) this;
        }

        public Criteria andWjqcountIn(List<String> values) {
            addCriterion("wjqCount in", values, "wjqcount");
            return (Criteria) this;
        }

        public Criteria andWjqcountNotIn(List<String> values) {
            addCriterion("wjqCount not in", values, "wjqcount");
            return (Criteria) this;
        }

        public Criteria andWjqcountBetween(String value1, String value2) {
            addCriterion("wjqCount between", value1, value2, "wjqcount");
            return (Criteria) this;
        }

        public Criteria andWjqcountNotBetween(String value1, String value2) {
            addCriterion("wjqCount not between", value1, value2, "wjqcount");
            return (Criteria) this;
        }

        public Criteria andJqcountIsNull() {
            addCriterion("jqCount is null");
            return (Criteria) this;
        }

        public Criteria andJqcountIsNotNull() {
            addCriterion("jqCount is not null");
            return (Criteria) this;
        }

        public Criteria andJqcountEqualTo(String value) {
            addCriterion("jqCount =", value, "jqcount");
            return (Criteria) this;
        }

        public Criteria andJqcountNotEqualTo(String value) {
            addCriterion("jqCount <>", value, "jqcount");
            return (Criteria) this;
        }

        public Criteria andJqcountGreaterThan(String value) {
            addCriterion("jqCount >", value, "jqcount");
            return (Criteria) this;
        }

        public Criteria andJqcountGreaterThanOrEqualTo(String value) {
            addCriterion("jqCount >=", value, "jqcount");
            return (Criteria) this;
        }

        public Criteria andJqcountLessThan(String value) {
            addCriterion("jqCount <", value, "jqcount");
            return (Criteria) this;
        }

        public Criteria andJqcountLessThanOrEqualTo(String value) {
            addCriterion("jqCount <=", value, "jqcount");
            return (Criteria) this;
        }

        public Criteria andJqcountLike(String value) {
            addCriterion("jqCount like", value, "jqcount");
            return (Criteria) this;
        }

        public Criteria andJqcountNotLike(String value) {
            addCriterion("jqCount not like", value, "jqcount");
            return (Criteria) this;
        }

        public Criteria andJqcountIn(List<String> values) {
            addCriterion("jqCount in", values, "jqcount");
            return (Criteria) this;
        }

        public Criteria andJqcountNotIn(List<String> values) {
            addCriterion("jqCount not in", values, "jqcount");
            return (Criteria) this;
        }

        public Criteria andJqcountBetween(String value1, String value2) {
            addCriterion("jqCount between", value1, value2, "jqcount");
            return (Criteria) this;
        }

        public Criteria andJqcountNotBetween(String value1, String value2) {
            addCriterion("jqCount not between", value1, value2, "jqcount");
            return (Criteria) this;
        }

        public Criteria andTotalcountIsNull() {
            addCriterion("totalCount is null");
            return (Criteria) this;
        }

        public Criteria andTotalcountIsNotNull() {
            addCriterion("totalCount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalcountEqualTo(String value) {
            addCriterion("totalCount =", value, "totalcount");
            return (Criteria) this;
        }

        public Criteria andTotalcountNotEqualTo(String value) {
            addCriterion("totalCount <>", value, "totalcount");
            return (Criteria) this;
        }

        public Criteria andTotalcountGreaterThan(String value) {
            addCriterion("totalCount >", value, "totalcount");
            return (Criteria) this;
        }

        public Criteria andTotalcountGreaterThanOrEqualTo(String value) {
            addCriterion("totalCount >=", value, "totalcount");
            return (Criteria) this;
        }

        public Criteria andTotalcountLessThan(String value) {
            addCriterion("totalCount <", value, "totalcount");
            return (Criteria) this;
        }

        public Criteria andTotalcountLessThanOrEqualTo(String value) {
            addCriterion("totalCount <=", value, "totalcount");
            return (Criteria) this;
        }

        public Criteria andTotalcountLike(String value) {
            addCriterion("totalCount like", value, "totalcount");
            return (Criteria) this;
        }

        public Criteria andTotalcountNotLike(String value) {
            addCriterion("totalCount not like", value, "totalcount");
            return (Criteria) this;
        }

        public Criteria andTotalcountIn(List<String> values) {
            addCriterion("totalCount in", values, "totalcount");
            return (Criteria) this;
        }

        public Criteria andTotalcountNotIn(List<String> values) {
            addCriterion("totalCount not in", values, "totalcount");
            return (Criteria) this;
        }

        public Criteria andTotalcountBetween(String value1, String value2) {
            addCriterion("totalCount between", value1, value2, "totalcount");
            return (Criteria) this;
        }

        public Criteria andTotalcountNotBetween(String value1, String value2) {
            addCriterion("totalCount not between", value1, value2, "totalcount");
            return (Criteria) this;
        }

        public Criteria andEwjqcountIsNull() {
            addCriterion("ewjqCount is null");
            return (Criteria) this;
        }

        public Criteria andEwjqcountIsNotNull() {
            addCriterion("ewjqCount is not null");
            return (Criteria) this;
        }

        public Criteria andEwjqcountEqualTo(String value) {
            addCriterion("ewjqCount =", value, "ewjqcount");
            return (Criteria) this;
        }

        public Criteria andEwjqcountNotEqualTo(String value) {
            addCriterion("ewjqCount <>", value, "ewjqcount");
            return (Criteria) this;
        }

        public Criteria andEwjqcountGreaterThan(String value) {
            addCriterion("ewjqCount >", value, "ewjqcount");
            return (Criteria) this;
        }

        public Criteria andEwjqcountGreaterThanOrEqualTo(String value) {
            addCriterion("ewjqCount >=", value, "ewjqcount");
            return (Criteria) this;
        }

        public Criteria andEwjqcountLessThan(String value) {
            addCriterion("ewjqCount <", value, "ewjqcount");
            return (Criteria) this;
        }

        public Criteria andEwjqcountLessThanOrEqualTo(String value) {
            addCriterion("ewjqCount <=", value, "ewjqcount");
            return (Criteria) this;
        }

        public Criteria andEwjqcountLike(String value) {
            addCriterion("ewjqCount like", value, "ewjqcount");
            return (Criteria) this;
        }

        public Criteria andEwjqcountNotLike(String value) {
            addCriterion("ewjqCount not like", value, "ewjqcount");
            return (Criteria) this;
        }

        public Criteria andEwjqcountIn(List<String> values) {
            addCriterion("ewjqCount in", values, "ewjqcount");
            return (Criteria) this;
        }

        public Criteria andEwjqcountNotIn(List<String> values) {
            addCriterion("ewjqCount not in", values, "ewjqcount");
            return (Criteria) this;
        }

        public Criteria andEwjqcountBetween(String value1, String value2) {
            addCriterion("ewjqCount between", value1, value2, "ewjqcount");
            return (Criteria) this;
        }

        public Criteria andEwjqcountNotBetween(String value1, String value2) {
            addCriterion("ewjqCount not between", value1, value2, "ewjqcount");
            return (Criteria) this;
        }

        public Criteria andEjqcountIsNull() {
            addCriterion("ejqCount is null");
            return (Criteria) this;
        }

        public Criteria andEjqcountIsNotNull() {
            addCriterion("ejqCount is not null");
            return (Criteria) this;
        }

        public Criteria andEjqcountEqualTo(String value) {
            addCriterion("ejqCount =", value, "ejqcount");
            return (Criteria) this;
        }

        public Criteria andEjqcountNotEqualTo(String value) {
            addCriterion("ejqCount <>", value, "ejqcount");
            return (Criteria) this;
        }

        public Criteria andEjqcountGreaterThan(String value) {
            addCriterion("ejqCount >", value, "ejqcount");
            return (Criteria) this;
        }

        public Criteria andEjqcountGreaterThanOrEqualTo(String value) {
            addCriterion("ejqCount >=", value, "ejqcount");
            return (Criteria) this;
        }

        public Criteria andEjqcountLessThan(String value) {
            addCriterion("ejqCount <", value, "ejqcount");
            return (Criteria) this;
        }

        public Criteria andEjqcountLessThanOrEqualTo(String value) {
            addCriterion("ejqCount <=", value, "ejqcount");
            return (Criteria) this;
        }

        public Criteria andEjqcountLike(String value) {
            addCriterion("ejqCount like", value, "ejqcount");
            return (Criteria) this;
        }

        public Criteria andEjqcountNotLike(String value) {
            addCriterion("ejqCount not like", value, "ejqcount");
            return (Criteria) this;
        }

        public Criteria andEjqcountIn(List<String> values) {
            addCriterion("ejqCount in", values, "ejqcount");
            return (Criteria) this;
        }

        public Criteria andEjqcountNotIn(List<String> values) {
            addCriterion("ejqCount not in", values, "ejqcount");
            return (Criteria) this;
        }

        public Criteria andEjqcountBetween(String value1, String value2) {
            addCriterion("ejqCount between", value1, value2, "ejqcount");
            return (Criteria) this;
        }

        public Criteria andEjqcountNotBetween(String value1, String value2) {
            addCriterion("ejqCount not between", value1, value2, "ejqcount");
            return (Criteria) this;
        }

        public Criteria andEtotalcountIsNull() {
            addCriterion("etotalCount is null");
            return (Criteria) this;
        }

        public Criteria andEtotalcountIsNotNull() {
            addCriterion("etotalCount is not null");
            return (Criteria) this;
        }

        public Criteria andEtotalcountEqualTo(String value) {
            addCriterion("etotalCount =", value, "etotalcount");
            return (Criteria) this;
        }

        public Criteria andEtotalcountNotEqualTo(String value) {
            addCriterion("etotalCount <>", value, "etotalcount");
            return (Criteria) this;
        }

        public Criteria andEtotalcountGreaterThan(String value) {
            addCriterion("etotalCount >", value, "etotalcount");
            return (Criteria) this;
        }

        public Criteria andEtotalcountGreaterThanOrEqualTo(String value) {
            addCriterion("etotalCount >=", value, "etotalcount");
            return (Criteria) this;
        }

        public Criteria andEtotalcountLessThan(String value) {
            addCriterion("etotalCount <", value, "etotalcount");
            return (Criteria) this;
        }

        public Criteria andEtotalcountLessThanOrEqualTo(String value) {
            addCriterion("etotalCount <=", value, "etotalcount");
            return (Criteria) this;
        }

        public Criteria andEtotalcountLike(String value) {
            addCriterion("etotalCount like", value, "etotalcount");
            return (Criteria) this;
        }

        public Criteria andEtotalcountNotLike(String value) {
            addCriterion("etotalCount not like", value, "etotalcount");
            return (Criteria) this;
        }

        public Criteria andEtotalcountIn(List<String> values) {
            addCriterion("etotalCount in", values, "etotalcount");
            return (Criteria) this;
        }

        public Criteria andEtotalcountNotIn(List<String> values) {
            addCriterion("etotalCount not in", values, "etotalcount");
            return (Criteria) this;
        }

        public Criteria andEtotalcountBetween(String value1, String value2) {
            addCriterion("etotalCount between", value1, value2, "etotalcount");
            return (Criteria) this;
        }

        public Criteria andEtotalcountNotBetween(String value1, String value2) {
            addCriterion("etotalCount not between", value1, value2, "etotalcount");
            return (Criteria) this;
        }

        public Criteria andApplyingcountIsNull() {
            addCriterion("applyingCount is null");
            return (Criteria) this;
        }

        public Criteria andApplyingcountIsNotNull() {
            addCriterion("applyingCount is not null");
            return (Criteria) this;
        }

        public Criteria andApplyingcountEqualTo(String value) {
            addCriterion("applyingCount =", value, "applyingcount");
            return (Criteria) this;
        }

        public Criteria andApplyingcountNotEqualTo(String value) {
            addCriterion("applyingCount <>", value, "applyingcount");
            return (Criteria) this;
        }

        public Criteria andApplyingcountGreaterThan(String value) {
            addCriterion("applyingCount >", value, "applyingcount");
            return (Criteria) this;
        }

        public Criteria andApplyingcountGreaterThanOrEqualTo(String value) {
            addCriterion("applyingCount >=", value, "applyingcount");
            return (Criteria) this;
        }

        public Criteria andApplyingcountLessThan(String value) {
            addCriterion("applyingCount <", value, "applyingcount");
            return (Criteria) this;
        }

        public Criteria andApplyingcountLessThanOrEqualTo(String value) {
            addCriterion("applyingCount <=", value, "applyingcount");
            return (Criteria) this;
        }

        public Criteria andApplyingcountLike(String value) {
            addCriterion("applyingCount like", value, "applyingcount");
            return (Criteria) this;
        }

        public Criteria andApplyingcountNotLike(String value) {
            addCriterion("applyingCount not like", value, "applyingcount");
            return (Criteria) this;
        }

        public Criteria andApplyingcountIn(List<String> values) {
            addCriterion("applyingCount in", values, "applyingcount");
            return (Criteria) this;
        }

        public Criteria andApplyingcountNotIn(List<String> values) {
            addCriterion("applyingCount not in", values, "applyingcount");
            return (Criteria) this;
        }

        public Criteria andApplyingcountBetween(String value1, String value2) {
            addCriterion("applyingCount between", value1, value2, "applyingcount");
            return (Criteria) this;
        }

        public Criteria andApplyingcountNotBetween(String value1, String value2) {
            addCriterion("applyingCount not between", value1, value2, "applyingcount");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountIsNull() {
            addCriterion("applyPassedCount is null");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountIsNotNull() {
            addCriterion("applyPassedCount is not null");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountEqualTo(String value) {
            addCriterion("applyPassedCount =", value, "applypassedcount");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountNotEqualTo(String value) {
            addCriterion("applyPassedCount <>", value, "applypassedcount");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountGreaterThan(String value) {
            addCriterion("applyPassedCount >", value, "applypassedcount");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountGreaterThanOrEqualTo(String value) {
            addCriterion("applyPassedCount >=", value, "applypassedcount");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountLessThan(String value) {
            addCriterion("applyPassedCount <", value, "applypassedcount");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountLessThanOrEqualTo(String value) {
            addCriterion("applyPassedCount <=", value, "applypassedcount");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountLike(String value) {
            addCriterion("applyPassedCount like", value, "applypassedcount");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountNotLike(String value) {
            addCriterion("applyPassedCount not like", value, "applypassedcount");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountIn(List<String> values) {
            addCriterion("applyPassedCount in", values, "applypassedcount");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountNotIn(List<String> values) {
            addCriterion("applyPassedCount not in", values, "applypassedcount");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountBetween(String value1, String value2) {
            addCriterion("applyPassedCount between", value1, value2, "applypassedcount");
            return (Criteria) this;
        }

        public Criteria andApplypassedcountNotBetween(String value1, String value2) {
            addCriterion("applyPassedCount not between", value1, value2, "applypassedcount");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountIsNull() {
            addCriterion("applyRejectCount is null");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountIsNotNull() {
            addCriterion("applyRejectCount is not null");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountEqualTo(String value) {
            addCriterion("applyRejectCount =", value, "applyrejectcount");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountNotEqualTo(String value) {
            addCriterion("applyRejectCount <>", value, "applyrejectcount");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountGreaterThan(String value) {
            addCriterion("applyRejectCount >", value, "applyrejectcount");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountGreaterThanOrEqualTo(String value) {
            addCriterion("applyRejectCount >=", value, "applyrejectcount");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountLessThan(String value) {
            addCriterion("applyRejectCount <", value, "applyrejectcount");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountLessThanOrEqualTo(String value) {
            addCriterion("applyRejectCount <=", value, "applyrejectcount");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountLike(String value) {
            addCriterion("applyRejectCount like", value, "applyrejectcount");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountNotLike(String value) {
            addCriterion("applyRejectCount not like", value, "applyrejectcount");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountIn(List<String> values) {
            addCriterion("applyRejectCount in", values, "applyrejectcount");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountNotIn(List<String> values) {
            addCriterion("applyRejectCount not in", values, "applyrejectcount");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountBetween(String value1, String value2) {
            addCriterion("applyRejectCount between", value1, value2, "applyrejectcount");
            return (Criteria) this;
        }

        public Criteria andApplyrejectcountNotBetween(String value1, String value2) {
            addCriterion("applyRejectCount not between", value1, value2, "applyrejectcount");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountIsNull() {
            addCriterion("applyTotalCount is null");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountIsNotNull() {
            addCriterion("applyTotalCount is not null");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountEqualTo(String value) {
            addCriterion("applyTotalCount =", value, "applytotalcount");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountNotEqualTo(String value) {
            addCriterion("applyTotalCount <>", value, "applytotalcount");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountGreaterThan(String value) {
            addCriterion("applyTotalCount >", value, "applytotalcount");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountGreaterThanOrEqualTo(String value) {
            addCriterion("applyTotalCount >=", value, "applytotalcount");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountLessThan(String value) {
            addCriterion("applyTotalCount <", value, "applytotalcount");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountLessThanOrEqualTo(String value) {
            addCriterion("applyTotalCount <=", value, "applytotalcount");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountLike(String value) {
            addCriterion("applyTotalCount like", value, "applytotalcount");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountNotLike(String value) {
            addCriterion("applyTotalCount not like", value, "applytotalcount");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountIn(List<String> values) {
            addCriterion("applyTotalCount in", values, "applytotalcount");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountNotIn(List<String> values) {
            addCriterion("applyTotalCount not in", values, "applytotalcount");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountBetween(String value1, String value2) {
            addCriterion("applyTotalCount between", value1, value2, "applytotalcount");
            return (Criteria) this;
        }

        public Criteria andApplytotalcountNotBetween(String value1, String value2) {
            addCriterion("applyTotalCount not between", value1, value2, "applytotalcount");
            return (Criteria) this;
        }

        public Criteria andQuerycountIsNull() {
            addCriterion("queryCount is null");
            return (Criteria) this;
        }

        public Criteria andQuerycountIsNotNull() {
            addCriterion("queryCount is not null");
            return (Criteria) this;
        }

        public Criteria andQuerycountEqualTo(String value) {
            addCriterion("queryCount =", value, "querycount");
            return (Criteria) this;
        }

        public Criteria andQuerycountNotEqualTo(String value) {
            addCriterion("queryCount <>", value, "querycount");
            return (Criteria) this;
        }

        public Criteria andQuerycountGreaterThan(String value) {
            addCriterion("queryCount >", value, "querycount");
            return (Criteria) this;
        }

        public Criteria andQuerycountGreaterThanOrEqualTo(String value) {
            addCriterion("queryCount >=", value, "querycount");
            return (Criteria) this;
        }

        public Criteria andQuerycountLessThan(String value) {
            addCriterion("queryCount <", value, "querycount");
            return (Criteria) this;
        }

        public Criteria andQuerycountLessThanOrEqualTo(String value) {
            addCriterion("queryCount <=", value, "querycount");
            return (Criteria) this;
        }

        public Criteria andQuerycountLike(String value) {
            addCriterion("queryCount like", value, "querycount");
            return (Criteria) this;
        }

        public Criteria andQuerycountNotLike(String value) {
            addCriterion("queryCount not like", value, "querycount");
            return (Criteria) this;
        }

        public Criteria andQuerycountIn(List<String> values) {
            addCriterion("queryCount in", values, "querycount");
            return (Criteria) this;
        }

        public Criteria andQuerycountNotIn(List<String> values) {
            addCriterion("queryCount not in", values, "querycount");
            return (Criteria) this;
        }

        public Criteria andQuerycountBetween(String value1, String value2) {
            addCriterion("queryCount between", value1, value2, "querycount");
            return (Criteria) this;
        }

        public Criteria andQuerycountNotBetween(String value1, String value2) {
            addCriterion("queryCount not between", value1, value2, "querycount");
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