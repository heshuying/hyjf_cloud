package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LinkExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public LinkExample() {
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

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("`type` not between", value1, value2, "type");
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

        public Criteria andOrderIsNull() {
            addCriterion("`order` is null");
            return (Criteria) this;
        }

        public Criteria andOrderIsNotNull() {
            addCriterion("`order` is not null");
            return (Criteria) this;
        }

        public Criteria andOrderEqualTo(Integer value) {
            addCriterion("`order` =", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotEqualTo(Integer value) {
            addCriterion("`order` <>", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThan(Integer value) {
            addCriterion("`order` >", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("`order` >=", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLessThan(Integer value) {
            addCriterion("`order` <", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLessThanOrEqualTo(Integer value) {
            addCriterion("`order` <=", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderIn(List<Integer> values) {
            addCriterion("`order` in", values, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotIn(List<Integer> values) {
            addCriterion("`order` not in", values, "order");
            return (Criteria) this;
        }

        public Criteria andOrderBetween(Integer value1, Integer value2) {
            addCriterion("`order` between", value1, value2, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("`order` not between", value1, value2, "order");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andWebnameIsNull() {
            addCriterion("webname is null");
            return (Criteria) this;
        }

        public Criteria andWebnameIsNotNull() {
            addCriterion("webname is not null");
            return (Criteria) this;
        }

        public Criteria andWebnameEqualTo(String value) {
            addCriterion("webname =", value, "webname");
            return (Criteria) this;
        }

        public Criteria andWebnameNotEqualTo(String value) {
            addCriterion("webname <>", value, "webname");
            return (Criteria) this;
        }

        public Criteria andWebnameGreaterThan(String value) {
            addCriterion("webname >", value, "webname");
            return (Criteria) this;
        }

        public Criteria andWebnameGreaterThanOrEqualTo(String value) {
            addCriterion("webname >=", value, "webname");
            return (Criteria) this;
        }

        public Criteria andWebnameLessThan(String value) {
            addCriterion("webname <", value, "webname");
            return (Criteria) this;
        }

        public Criteria andWebnameLessThanOrEqualTo(String value) {
            addCriterion("webname <=", value, "webname");
            return (Criteria) this;
        }

        public Criteria andWebnameLike(String value) {
            addCriterion("webname like", value, "webname");
            return (Criteria) this;
        }

        public Criteria andWebnameNotLike(String value) {
            addCriterion("webname not like", value, "webname");
            return (Criteria) this;
        }

        public Criteria andWebnameIn(List<String> values) {
            addCriterion("webname in", values, "webname");
            return (Criteria) this;
        }

        public Criteria andWebnameNotIn(List<String> values) {
            addCriterion("webname not in", values, "webname");
            return (Criteria) this;
        }

        public Criteria andWebnameBetween(String value1, String value2) {
            addCriterion("webname between", value1, value2, "webname");
            return (Criteria) this;
        }

        public Criteria andWebnameNotBetween(String value1, String value2) {
            addCriterion("webname not between", value1, value2, "webname");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNull() {
            addCriterion("summary is null");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNotNull() {
            addCriterion("summary is not null");
            return (Criteria) this;
        }

        public Criteria andSummaryEqualTo(String value) {
            addCriterion("summary =", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotEqualTo(String value) {
            addCriterion("summary <>", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThan(String value) {
            addCriterion("summary >", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("summary >=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThan(String value) {
            addCriterion("summary <", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThanOrEqualTo(String value) {
            addCriterion("summary <=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLike(String value) {
            addCriterion("summary like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotLike(String value) {
            addCriterion("summary not like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryIn(List<String> values) {
            addCriterion("summary in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotIn(List<String> values) {
            addCriterion("summary not in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryBetween(String value1, String value2) {
            addCriterion("summary between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotBetween(String value1, String value2) {
            addCriterion("summary not between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andSummary2IsNull() {
            addCriterion("summary2 is null");
            return (Criteria) this;
        }

        public Criteria andSummary2IsNotNull() {
            addCriterion("summary2 is not null");
            return (Criteria) this;
        }

        public Criteria andSummary2EqualTo(String value) {
            addCriterion("summary2 =", value, "summary2");
            return (Criteria) this;
        }

        public Criteria andSummary2NotEqualTo(String value) {
            addCriterion("summary2 <>", value, "summary2");
            return (Criteria) this;
        }

        public Criteria andSummary2GreaterThan(String value) {
            addCriterion("summary2 >", value, "summary2");
            return (Criteria) this;
        }

        public Criteria andSummary2GreaterThanOrEqualTo(String value) {
            addCriterion("summary2 >=", value, "summary2");
            return (Criteria) this;
        }

        public Criteria andSummary2LessThan(String value) {
            addCriterion("summary2 <", value, "summary2");
            return (Criteria) this;
        }

        public Criteria andSummary2LessThanOrEqualTo(String value) {
            addCriterion("summary2 <=", value, "summary2");
            return (Criteria) this;
        }

        public Criteria andSummary2Like(String value) {
            addCriterion("summary2 like", value, "summary2");
            return (Criteria) this;
        }

        public Criteria andSummary2NotLike(String value) {
            addCriterion("summary2 not like", value, "summary2");
            return (Criteria) this;
        }

        public Criteria andSummary2In(List<String> values) {
            addCriterion("summary2 in", values, "summary2");
            return (Criteria) this;
        }

        public Criteria andSummary2NotIn(List<String> values) {
            addCriterion("summary2 not in", values, "summary2");
            return (Criteria) this;
        }

        public Criteria andSummary2Between(String value1, String value2) {
            addCriterion("summary2 between", value1, value2, "summary2");
            return (Criteria) this;
        }

        public Criteria andSummary2NotBetween(String value1, String value2) {
            addCriterion("summary2 not between", value1, value2, "summary2");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresIsNull() {
            addCriterion("control_measures is null");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresIsNotNull() {
            addCriterion("control_measures is not null");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresEqualTo(String value) {
            addCriterion("control_measures =", value, "controlMeasures");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresNotEqualTo(String value) {
            addCriterion("control_measures <>", value, "controlMeasures");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresGreaterThan(String value) {
            addCriterion("control_measures >", value, "controlMeasures");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresGreaterThanOrEqualTo(String value) {
            addCriterion("control_measures >=", value, "controlMeasures");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresLessThan(String value) {
            addCriterion("control_measures <", value, "controlMeasures");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresLessThanOrEqualTo(String value) {
            addCriterion("control_measures <=", value, "controlMeasures");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresLike(String value) {
            addCriterion("control_measures like", value, "controlMeasures");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresNotLike(String value) {
            addCriterion("control_measures not like", value, "controlMeasures");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresIn(List<String> values) {
            addCriterion("control_measures in", values, "controlMeasures");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresNotIn(List<String> values) {
            addCriterion("control_measures not in", values, "controlMeasures");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresBetween(String value1, String value2) {
            addCriterion("control_measures between", value1, value2, "controlMeasures");
            return (Criteria) this;
        }

        public Criteria andControlMeasuresNotBetween(String value1, String value2) {
            addCriterion("control_measures not between", value1, value2, "controlMeasures");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessIsNull() {
            addCriterion("operating_process is null");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessIsNotNull() {
            addCriterion("operating_process is not null");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessEqualTo(String value) {
            addCriterion("operating_process =", value, "operatingProcess");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessNotEqualTo(String value) {
            addCriterion("operating_process <>", value, "operatingProcess");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessGreaterThan(String value) {
            addCriterion("operating_process >", value, "operatingProcess");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessGreaterThanOrEqualTo(String value) {
            addCriterion("operating_process >=", value, "operatingProcess");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessLessThan(String value) {
            addCriterion("operating_process <", value, "operatingProcess");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessLessThanOrEqualTo(String value) {
            addCriterion("operating_process <=", value, "operatingProcess");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessLike(String value) {
            addCriterion("operating_process like", value, "operatingProcess");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessNotLike(String value) {
            addCriterion("operating_process not like", value, "operatingProcess");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessIn(List<String> values) {
            addCriterion("operating_process in", values, "operatingProcess");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessNotIn(List<String> values) {
            addCriterion("operating_process not in", values, "operatingProcess");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessBetween(String value1, String value2) {
            addCriterion("operating_process between", value1, value2, "operatingProcess");
            return (Criteria) this;
        }

        public Criteria andOperatingProcessNotBetween(String value1, String value2) {
            addCriterion("operating_process not between", value1, value2, "operatingProcess");
            return (Criteria) this;
        }

        public Criteria andLogoIsNull() {
            addCriterion("logo is null");
            return (Criteria) this;
        }

        public Criteria andLogoIsNotNull() {
            addCriterion("logo is not null");
            return (Criteria) this;
        }

        public Criteria andLogoEqualTo(String value) {
            addCriterion("logo =", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotEqualTo(String value) {
            addCriterion("logo <>", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThan(String value) {
            addCriterion("logo >", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThanOrEqualTo(String value) {
            addCriterion("logo >=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThan(String value) {
            addCriterion("logo <", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThanOrEqualTo(String value) {
            addCriterion("logo <=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLike(String value) {
            addCriterion("logo like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotLike(String value) {
            addCriterion("logo not like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoIn(List<String> values) {
            addCriterion("logo in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotIn(List<String> values) {
            addCriterion("logo not in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoBetween(String value1, String value2) {
            addCriterion("logo between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotBetween(String value1, String value2) {
            addCriterion("logo not between", value1, value2, "logo");
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

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andSetupTimeIsNull() {
            addCriterion("setup_time is null");
            return (Criteria) this;
        }

        public Criteria andSetupTimeIsNotNull() {
            addCriterion("setup_time is not null");
            return (Criteria) this;
        }

        public Criteria andSetupTimeEqualTo(String value) {
            addCriterion("setup_time =", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeNotEqualTo(String value) {
            addCriterion("setup_time <>", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeGreaterThan(String value) {
            addCriterion("setup_time >", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeGreaterThanOrEqualTo(String value) {
            addCriterion("setup_time >=", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeLessThan(String value) {
            addCriterion("setup_time <", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeLessThanOrEqualTo(String value) {
            addCriterion("setup_time <=", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeLike(String value) {
            addCriterion("setup_time like", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeNotLike(String value) {
            addCriterion("setup_time not like", value, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeIn(List<String> values) {
            addCriterion("setup_time in", values, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeNotIn(List<String> values) {
            addCriterion("setup_time not in", values, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeBetween(String value1, String value2) {
            addCriterion("setup_time between", value1, value2, "setupTime");
            return (Criteria) this;
        }

        public Criteria andSetupTimeNotBetween(String value1, String value2) {
            addCriterion("setup_time not between", value1, value2, "setupTime");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeIsNull() {
            addCriterion("cooperation_time is null");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeIsNotNull() {
            addCriterion("cooperation_time is not null");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeEqualTo(String value) {
            addCriterion("cooperation_time =", value, "cooperationTime");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeNotEqualTo(String value) {
            addCriterion("cooperation_time <>", value, "cooperationTime");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeGreaterThan(String value) {
            addCriterion("cooperation_time >", value, "cooperationTime");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeGreaterThanOrEqualTo(String value) {
            addCriterion("cooperation_time >=", value, "cooperationTime");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeLessThan(String value) {
            addCriterion("cooperation_time <", value, "cooperationTime");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeLessThanOrEqualTo(String value) {
            addCriterion("cooperation_time <=", value, "cooperationTime");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeLike(String value) {
            addCriterion("cooperation_time like", value, "cooperationTime");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeNotLike(String value) {
            addCriterion("cooperation_time not like", value, "cooperationTime");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeIn(List<String> values) {
            addCriterion("cooperation_time in", values, "cooperationTime");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeNotIn(List<String> values) {
            addCriterion("cooperation_time not in", values, "cooperationTime");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeBetween(String value1, String value2) {
            addCriterion("cooperation_time between", value1, value2, "cooperationTime");
            return (Criteria) this;
        }

        public Criteria andCooperationTimeNotBetween(String value1, String value2) {
            addCriterion("cooperation_time not between", value1, value2, "cooperationTime");
            return (Criteria) this;
        }

        public Criteria andLogo1IsNull() {
            addCriterion("logo1 is null");
            return (Criteria) this;
        }

        public Criteria andLogo1IsNotNull() {
            addCriterion("logo1 is not null");
            return (Criteria) this;
        }

        public Criteria andLogo1EqualTo(String value) {
            addCriterion("logo1 =", value, "logo1");
            return (Criteria) this;
        }

        public Criteria andLogo1NotEqualTo(String value) {
            addCriterion("logo1 <>", value, "logo1");
            return (Criteria) this;
        }

        public Criteria andLogo1GreaterThan(String value) {
            addCriterion("logo1 >", value, "logo1");
            return (Criteria) this;
        }

        public Criteria andLogo1GreaterThanOrEqualTo(String value) {
            addCriterion("logo1 >=", value, "logo1");
            return (Criteria) this;
        }

        public Criteria andLogo1LessThan(String value) {
            addCriterion("logo1 <", value, "logo1");
            return (Criteria) this;
        }

        public Criteria andLogo1LessThanOrEqualTo(String value) {
            addCriterion("logo1 <=", value, "logo1");
            return (Criteria) this;
        }

        public Criteria andLogo1Like(String value) {
            addCriterion("logo1 like", value, "logo1");
            return (Criteria) this;
        }

        public Criteria andLogo1NotLike(String value) {
            addCriterion("logo1 not like", value, "logo1");
            return (Criteria) this;
        }

        public Criteria andLogo1In(List<String> values) {
            addCriterion("logo1 in", values, "logo1");
            return (Criteria) this;
        }

        public Criteria andLogo1NotIn(List<String> values) {
            addCriterion("logo1 not in", values, "logo1");
            return (Criteria) this;
        }

        public Criteria andLogo1Between(String value1, String value2) {
            addCriterion("logo1 between", value1, value2, "logo1");
            return (Criteria) this;
        }

        public Criteria andLogo1NotBetween(String value1, String value2) {
            addCriterion("logo1 not between", value1, value2, "logo1");
            return (Criteria) this;
        }

        public Criteria andApprovalByIsNull() {
            addCriterion("approval_by is null");
            return (Criteria) this;
        }

        public Criteria andApprovalByIsNotNull() {
            addCriterion("approval_by is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalByEqualTo(String value) {
            addCriterion("approval_by =", value, "approvalBy");
            return (Criteria) this;
        }

        public Criteria andApprovalByNotEqualTo(String value) {
            addCriterion("approval_by <>", value, "approvalBy");
            return (Criteria) this;
        }

        public Criteria andApprovalByGreaterThan(String value) {
            addCriterion("approval_by >", value, "approvalBy");
            return (Criteria) this;
        }

        public Criteria andApprovalByGreaterThanOrEqualTo(String value) {
            addCriterion("approval_by >=", value, "approvalBy");
            return (Criteria) this;
        }

        public Criteria andApprovalByLessThan(String value) {
            addCriterion("approval_by <", value, "approvalBy");
            return (Criteria) this;
        }

        public Criteria andApprovalByLessThanOrEqualTo(String value) {
            addCriterion("approval_by <=", value, "approvalBy");
            return (Criteria) this;
        }

        public Criteria andApprovalByLike(String value) {
            addCriterion("approval_by like", value, "approvalBy");
            return (Criteria) this;
        }

        public Criteria andApprovalByNotLike(String value) {
            addCriterion("approval_by not like", value, "approvalBy");
            return (Criteria) this;
        }

        public Criteria andApprovalByIn(List<String> values) {
            addCriterion("approval_by in", values, "approvalBy");
            return (Criteria) this;
        }

        public Criteria andApprovalByNotIn(List<String> values) {
            addCriterion("approval_by not in", values, "approvalBy");
            return (Criteria) this;
        }

        public Criteria andApprovalByBetween(String value1, String value2) {
            addCriterion("approval_by between", value1, value2, "approvalBy");
            return (Criteria) this;
        }

        public Criteria andApprovalByNotBetween(String value1, String value2) {
            addCriterion("approval_by not between", value1, value2, "approvalBy");
            return (Criteria) this;
        }

        public Criteria andRegisterCapitalIsNull() {
            addCriterion("register_capital is null");
            return (Criteria) this;
        }

        public Criteria andRegisterCapitalIsNotNull() {
            addCriterion("register_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRegisterCapitalEqualTo(Integer value) {
            addCriterion("register_capital =", value, "registerCapital");
            return (Criteria) this;
        }

        public Criteria andRegisterCapitalNotEqualTo(Integer value) {
            addCriterion("register_capital <>", value, "registerCapital");
            return (Criteria) this;
        }

        public Criteria andRegisterCapitalGreaterThan(Integer value) {
            addCriterion("register_capital >", value, "registerCapital");
            return (Criteria) this;
        }

        public Criteria andRegisterCapitalGreaterThanOrEqualTo(Integer value) {
            addCriterion("register_capital >=", value, "registerCapital");
            return (Criteria) this;
        }

        public Criteria andRegisterCapitalLessThan(Integer value) {
            addCriterion("register_capital <", value, "registerCapital");
            return (Criteria) this;
        }

        public Criteria andRegisterCapitalLessThanOrEqualTo(Integer value) {
            addCriterion("register_capital <=", value, "registerCapital");
            return (Criteria) this;
        }

        public Criteria andRegisterCapitalIn(List<Integer> values) {
            addCriterion("register_capital in", values, "registerCapital");
            return (Criteria) this;
        }

        public Criteria andRegisterCapitalNotIn(List<Integer> values) {
            addCriterion("register_capital not in", values, "registerCapital");
            return (Criteria) this;
        }

        public Criteria andRegisterCapitalBetween(Integer value1, Integer value2) {
            addCriterion("register_capital between", value1, value2, "registerCapital");
            return (Criteria) this;
        }

        public Criteria andRegisterCapitalNotBetween(Integer value1, Integer value2) {
            addCriterion("register_capital not between", value1, value2, "registerCapital");
            return (Criteria) this;
        }

        public Criteria andPartnerTypeIsNull() {
            addCriterion("partner_type is null");
            return (Criteria) this;
        }

        public Criteria andPartnerTypeIsNotNull() {
            addCriterion("partner_type is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerTypeEqualTo(Integer value) {
            addCriterion("partner_type =", value, "partnerType");
            return (Criteria) this;
        }

        public Criteria andPartnerTypeNotEqualTo(Integer value) {
            addCriterion("partner_type <>", value, "partnerType");
            return (Criteria) this;
        }

        public Criteria andPartnerTypeGreaterThan(Integer value) {
            addCriterion("partner_type >", value, "partnerType");
            return (Criteria) this;
        }

        public Criteria andPartnerTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("partner_type >=", value, "partnerType");
            return (Criteria) this;
        }

        public Criteria andPartnerTypeLessThan(Integer value) {
            addCriterion("partner_type <", value, "partnerType");
            return (Criteria) this;
        }

        public Criteria andPartnerTypeLessThanOrEqualTo(Integer value) {
            addCriterion("partner_type <=", value, "partnerType");
            return (Criteria) this;
        }

        public Criteria andPartnerTypeIn(List<Integer> values) {
            addCriterion("partner_type in", values, "partnerType");
            return (Criteria) this;
        }

        public Criteria andPartnerTypeNotIn(List<Integer> values) {
            addCriterion("partner_type not in", values, "partnerType");
            return (Criteria) this;
        }

        public Criteria andPartnerTypeBetween(Integer value1, Integer value2) {
            addCriterion("partner_type between", value1, value2, "partnerType");
            return (Criteria) this;
        }

        public Criteria andPartnerTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("partner_type not between", value1, value2, "partnerType");
            return (Criteria) this;
        }

        public Criteria andHitsIsNull() {
            addCriterion("hits is null");
            return (Criteria) this;
        }

        public Criteria andHitsIsNotNull() {
            addCriterion("hits is not null");
            return (Criteria) this;
        }

        public Criteria andHitsEqualTo(Integer value) {
            addCriterion("hits =", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsNotEqualTo(Integer value) {
            addCriterion("hits <>", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsGreaterThan(Integer value) {
            addCriterion("hits >", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsGreaterThanOrEqualTo(Integer value) {
            addCriterion("hits >=", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsLessThan(Integer value) {
            addCriterion("hits <", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsLessThanOrEqualTo(Integer value) {
            addCriterion("hits <=", value, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsIn(List<Integer> values) {
            addCriterion("hits in", values, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsNotIn(List<Integer> values) {
            addCriterion("hits not in", values, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsBetween(Integer value1, Integer value2) {
            addCriterion("hits between", value1, value2, "hits");
            return (Criteria) this;
        }

        public Criteria andHitsNotBetween(Integer value1, Integer value2) {
            addCriterion("hits not between", value1, value2, "hits");
            return (Criteria) this;
        }

        public Criteria andIsindexIsNull() {
            addCriterion("isindex is null");
            return (Criteria) this;
        }

        public Criteria andIsindexIsNotNull() {
            addCriterion("isindex is not null");
            return (Criteria) this;
        }

        public Criteria andIsindexEqualTo(Integer value) {
            addCriterion("isindex =", value, "isindex");
            return (Criteria) this;
        }

        public Criteria andIsindexNotEqualTo(Integer value) {
            addCriterion("isindex <>", value, "isindex");
            return (Criteria) this;
        }

        public Criteria andIsindexGreaterThan(Integer value) {
            addCriterion("isindex >", value, "isindex");
            return (Criteria) this;
        }

        public Criteria andIsindexGreaterThanOrEqualTo(Integer value) {
            addCriterion("isindex >=", value, "isindex");
            return (Criteria) this;
        }

        public Criteria andIsindexLessThan(Integer value) {
            addCriterion("isindex <", value, "isindex");
            return (Criteria) this;
        }

        public Criteria andIsindexLessThanOrEqualTo(Integer value) {
            addCriterion("isindex <=", value, "isindex");
            return (Criteria) this;
        }

        public Criteria andIsindexIn(List<Integer> values) {
            addCriterion("isindex in", values, "isindex");
            return (Criteria) this;
        }

        public Criteria andIsindexNotIn(List<Integer> values) {
            addCriterion("isindex not in", values, "isindex");
            return (Criteria) this;
        }

        public Criteria andIsindexBetween(Integer value1, Integer value2) {
            addCriterion("isindex between", value1, value2, "isindex");
            return (Criteria) this;
        }

        public Criteria andIsindexNotBetween(Integer value1, Integer value2) {
            addCriterion("isindex not between", value1, value2, "isindex");
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