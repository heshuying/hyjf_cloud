package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompanyInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CompanyInfoExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andProvenceIsNull() {
            addCriterion("provence is null");
            return (Criteria) this;
        }

        public Criteria andProvenceIsNotNull() {
            addCriterion("provence is not null");
            return (Criteria) this;
        }

        public Criteria andProvenceEqualTo(String value) {
            addCriterion("provence =", value, "provence");
            return (Criteria) this;
        }

        public Criteria andProvenceNotEqualTo(String value) {
            addCriterion("provence <>", value, "provence");
            return (Criteria) this;
        }

        public Criteria andProvenceGreaterThan(String value) {
            addCriterion("provence >", value, "provence");
            return (Criteria) this;
        }

        public Criteria andProvenceGreaterThanOrEqualTo(String value) {
            addCriterion("provence >=", value, "provence");
            return (Criteria) this;
        }

        public Criteria andProvenceLessThan(String value) {
            addCriterion("provence <", value, "provence");
            return (Criteria) this;
        }

        public Criteria andProvenceLessThanOrEqualTo(String value) {
            addCriterion("provence <=", value, "provence");
            return (Criteria) this;
        }

        public Criteria andProvenceLike(String value) {
            addCriterion("provence like", value, "provence");
            return (Criteria) this;
        }

        public Criteria andProvenceNotLike(String value) {
            addCriterion("provence not like", value, "provence");
            return (Criteria) this;
        }

        public Criteria andProvenceIn(List<String> values) {
            addCriterion("provence in", values, "provence");
            return (Criteria) this;
        }

        public Criteria andProvenceNotIn(List<String> values) {
            addCriterion("provence not in", values, "provence");
            return (Criteria) this;
        }

        public Criteria andProvenceBetween(String value1, String value2) {
            addCriterion("provence between", value1, value2, "provence");
            return (Criteria) this;
        }

        public Criteria andProvenceNotBetween(String value1, String value2) {
            addCriterion("provence not between", value1, value2, "provence");
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

        public Criteria andRegistrationTimeIsNull() {
            addCriterion("registration_time is null");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeIsNotNull() {
            addCriterion("registration_time is not null");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeEqualTo(String value) {
            addCriterion("registration_time =", value, "registrationTime");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeNotEqualTo(String value) {
            addCriterion("registration_time <>", value, "registrationTime");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeGreaterThan(String value) {
            addCriterion("registration_time >", value, "registrationTime");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeGreaterThanOrEqualTo(String value) {
            addCriterion("registration_time >=", value, "registrationTime");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeLessThan(String value) {
            addCriterion("registration_time <", value, "registrationTime");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeLessThanOrEqualTo(String value) {
            addCriterion("registration_time <=", value, "registrationTime");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeLike(String value) {
            addCriterion("registration_time like", value, "registrationTime");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeNotLike(String value) {
            addCriterion("registration_time not like", value, "registrationTime");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeIn(List<String> values) {
            addCriterion("registration_time in", values, "registrationTime");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeNotIn(List<String> values) {
            addCriterion("registration_time not in", values, "registrationTime");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeBetween(String value1, String value2) {
            addCriterion("registration_time between", value1, value2, "registrationTime");
            return (Criteria) this;
        }

        public Criteria andRegistrationTimeNotBetween(String value1, String value2) {
            addCriterion("registration_time not between", value1, value2, "registrationTime");
            return (Criteria) this;
        }

        public Criteria andBriefIsNull() {
            addCriterion("brief is null");
            return (Criteria) this;
        }

        public Criteria andBriefIsNotNull() {
            addCriterion("brief is not null");
            return (Criteria) this;
        }

        public Criteria andBriefEqualTo(String value) {
            addCriterion("brief =", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefNotEqualTo(String value) {
            addCriterion("brief <>", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefGreaterThan(String value) {
            addCriterion("brief >", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefGreaterThanOrEqualTo(String value) {
            addCriterion("brief >=", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefLessThan(String value) {
            addCriterion("brief <", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefLessThanOrEqualTo(String value) {
            addCriterion("brief <=", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefLike(String value) {
            addCriterion("brief like", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefNotLike(String value) {
            addCriterion("brief not like", value, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefIn(List<String> values) {
            addCriterion("brief in", values, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefNotIn(List<String> values) {
            addCriterion("brief not in", values, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefBetween(String value1, String value2) {
            addCriterion("brief between", value1, value2, "brief");
            return (Criteria) this;
        }

        public Criteria andBriefNotBetween(String value1, String value2) {
            addCriterion("brief not between", value1, value2, "brief");
            return (Criteria) this;
        }

        public Criteria andTelIsNull() {
            addCriterion("tel is null");
            return (Criteria) this;
        }

        public Criteria andTelIsNotNull() {
            addCriterion("tel is not null");
            return (Criteria) this;
        }

        public Criteria andTelEqualTo(String value) {
            addCriterion("tel =", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotEqualTo(String value) {
            addCriterion("tel <>", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelGreaterThan(String value) {
            addCriterion("tel >", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelGreaterThanOrEqualTo(String value) {
            addCriterion("tel >=", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLessThan(String value) {
            addCriterion("tel <", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLessThanOrEqualTo(String value) {
            addCriterion("tel <=", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLike(String value) {
            addCriterion("tel like", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotLike(String value) {
            addCriterion("tel not like", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelIn(List<String> values) {
            addCriterion("tel in", values, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotIn(List<String> values) {
            addCriterion("tel not in", values, "tel");
            return (Criteria) this;
        }

        public Criteria andTelBetween(String value1, String value2) {
            addCriterion("tel between", value1, value2, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotBetween(String value1, String value2) {
            addCriterion("tel not between", value1, value2, "tel");
            return (Criteria) this;
        }

        public Criteria andBannerIsNull() {
            addCriterion("banner is null");
            return (Criteria) this;
        }

        public Criteria andBannerIsNotNull() {
            addCriterion("banner is not null");
            return (Criteria) this;
        }

        public Criteria andBannerEqualTo(String value) {
            addCriterion("banner =", value, "banner");
            return (Criteria) this;
        }

        public Criteria andBannerNotEqualTo(String value) {
            addCriterion("banner <>", value, "banner");
            return (Criteria) this;
        }

        public Criteria andBannerGreaterThan(String value) {
            addCriterion("banner >", value, "banner");
            return (Criteria) this;
        }

        public Criteria andBannerGreaterThanOrEqualTo(String value) {
            addCriterion("banner >=", value, "banner");
            return (Criteria) this;
        }

        public Criteria andBannerLessThan(String value) {
            addCriterion("banner <", value, "banner");
            return (Criteria) this;
        }

        public Criteria andBannerLessThanOrEqualTo(String value) {
            addCriterion("banner <=", value, "banner");
            return (Criteria) this;
        }

        public Criteria andBannerLike(String value) {
            addCriterion("banner like", value, "banner");
            return (Criteria) this;
        }

        public Criteria andBannerNotLike(String value) {
            addCriterion("banner not like", value, "banner");
            return (Criteria) this;
        }

        public Criteria andBannerIn(List<String> values) {
            addCriterion("banner in", values, "banner");
            return (Criteria) this;
        }

        public Criteria andBannerNotIn(List<String> values) {
            addCriterion("banner not in", values, "banner");
            return (Criteria) this;
        }

        public Criteria andBannerBetween(String value1, String value2) {
            addCriterion("banner between", value1, value2, "banner");
            return (Criteria) this;
        }

        public Criteria andBannerNotBetween(String value1, String value2) {
            addCriterion("banner not between", value1, value2, "banner");
            return (Criteria) this;
        }

        public Criteria andRegImg1IsNull() {
            addCriterion("reg_img1 is null");
            return (Criteria) this;
        }

        public Criteria andRegImg1IsNotNull() {
            addCriterion("reg_img1 is not null");
            return (Criteria) this;
        }

        public Criteria andRegImg1EqualTo(String value) {
            addCriterion("reg_img1 =", value, "regImg1");
            return (Criteria) this;
        }

        public Criteria andRegImg1NotEqualTo(String value) {
            addCriterion("reg_img1 <>", value, "regImg1");
            return (Criteria) this;
        }

        public Criteria andRegImg1GreaterThan(String value) {
            addCriterion("reg_img1 >", value, "regImg1");
            return (Criteria) this;
        }

        public Criteria andRegImg1GreaterThanOrEqualTo(String value) {
            addCriterion("reg_img1 >=", value, "regImg1");
            return (Criteria) this;
        }

        public Criteria andRegImg1LessThan(String value) {
            addCriterion("reg_img1 <", value, "regImg1");
            return (Criteria) this;
        }

        public Criteria andRegImg1LessThanOrEqualTo(String value) {
            addCriterion("reg_img1 <=", value, "regImg1");
            return (Criteria) this;
        }

        public Criteria andRegImg1Like(String value) {
            addCriterion("reg_img1 like", value, "regImg1");
            return (Criteria) this;
        }

        public Criteria andRegImg1NotLike(String value) {
            addCriterion("reg_img1 not like", value, "regImg1");
            return (Criteria) this;
        }

        public Criteria andRegImg1In(List<String> values) {
            addCriterion("reg_img1 in", values, "regImg1");
            return (Criteria) this;
        }

        public Criteria andRegImg1NotIn(List<String> values) {
            addCriterion("reg_img1 not in", values, "regImg1");
            return (Criteria) this;
        }

        public Criteria andRegImg1Between(String value1, String value2) {
            addCriterion("reg_img1 between", value1, value2, "regImg1");
            return (Criteria) this;
        }

        public Criteria andRegImg1NotBetween(String value1, String value2) {
            addCriterion("reg_img1 not between", value1, value2, "regImg1");
            return (Criteria) this;
        }

        public Criteria andRegImg2IsNull() {
            addCriterion("reg_img2 is null");
            return (Criteria) this;
        }

        public Criteria andRegImg2IsNotNull() {
            addCriterion("reg_img2 is not null");
            return (Criteria) this;
        }

        public Criteria andRegImg2EqualTo(String value) {
            addCriterion("reg_img2 =", value, "regImg2");
            return (Criteria) this;
        }

        public Criteria andRegImg2NotEqualTo(String value) {
            addCriterion("reg_img2 <>", value, "regImg2");
            return (Criteria) this;
        }

        public Criteria andRegImg2GreaterThan(String value) {
            addCriterion("reg_img2 >", value, "regImg2");
            return (Criteria) this;
        }

        public Criteria andRegImg2GreaterThanOrEqualTo(String value) {
            addCriterion("reg_img2 >=", value, "regImg2");
            return (Criteria) this;
        }

        public Criteria andRegImg2LessThan(String value) {
            addCriterion("reg_img2 <", value, "regImg2");
            return (Criteria) this;
        }

        public Criteria andRegImg2LessThanOrEqualTo(String value) {
            addCriterion("reg_img2 <=", value, "regImg2");
            return (Criteria) this;
        }

        public Criteria andRegImg2Like(String value) {
            addCriterion("reg_img2 like", value, "regImg2");
            return (Criteria) this;
        }

        public Criteria andRegImg2NotLike(String value) {
            addCriterion("reg_img2 not like", value, "regImg2");
            return (Criteria) this;
        }

        public Criteria andRegImg2In(List<String> values) {
            addCriterion("reg_img2 in", values, "regImg2");
            return (Criteria) this;
        }

        public Criteria andRegImg2NotIn(List<String> values) {
            addCriterion("reg_img2 not in", values, "regImg2");
            return (Criteria) this;
        }

        public Criteria andRegImg2Between(String value1, String value2) {
            addCriterion("reg_img2 between", value1, value2, "regImg2");
            return (Criteria) this;
        }

        public Criteria andRegImg2NotBetween(String value1, String value2) {
            addCriterion("reg_img2 not between", value1, value2, "regImg2");
            return (Criteria) this;
        }

        public Criteria andRegImg3IsNull() {
            addCriterion("reg_img3 is null");
            return (Criteria) this;
        }

        public Criteria andRegImg3IsNotNull() {
            addCriterion("reg_img3 is not null");
            return (Criteria) this;
        }

        public Criteria andRegImg3EqualTo(String value) {
            addCriterion("reg_img3 =", value, "regImg3");
            return (Criteria) this;
        }

        public Criteria andRegImg3NotEqualTo(String value) {
            addCriterion("reg_img3 <>", value, "regImg3");
            return (Criteria) this;
        }

        public Criteria andRegImg3GreaterThan(String value) {
            addCriterion("reg_img3 >", value, "regImg3");
            return (Criteria) this;
        }

        public Criteria andRegImg3GreaterThanOrEqualTo(String value) {
            addCriterion("reg_img3 >=", value, "regImg3");
            return (Criteria) this;
        }

        public Criteria andRegImg3LessThan(String value) {
            addCriterion("reg_img3 <", value, "regImg3");
            return (Criteria) this;
        }

        public Criteria andRegImg3LessThanOrEqualTo(String value) {
            addCriterion("reg_img3 <=", value, "regImg3");
            return (Criteria) this;
        }

        public Criteria andRegImg3Like(String value) {
            addCriterion("reg_img3 like", value, "regImg3");
            return (Criteria) this;
        }

        public Criteria andRegImg3NotLike(String value) {
            addCriterion("reg_img3 not like", value, "regImg3");
            return (Criteria) this;
        }

        public Criteria andRegImg3In(List<String> values) {
            addCriterion("reg_img3 in", values, "regImg3");
            return (Criteria) this;
        }

        public Criteria andRegImg3NotIn(List<String> values) {
            addCriterion("reg_img3 not in", values, "regImg3");
            return (Criteria) this;
        }

        public Criteria andRegImg3Between(String value1, String value2) {
            addCriterion("reg_img3 between", value1, value2, "regImg3");
            return (Criteria) this;
        }

        public Criteria andRegImg3NotBetween(String value1, String value2) {
            addCriterion("reg_img3 not between", value1, value2, "regImg3");
            return (Criteria) this;
        }

        public Criteria andRegImg4IsNull() {
            addCriterion("reg_img4 is null");
            return (Criteria) this;
        }

        public Criteria andRegImg4IsNotNull() {
            addCriterion("reg_img4 is not null");
            return (Criteria) this;
        }

        public Criteria andRegImg4EqualTo(String value) {
            addCriterion("reg_img4 =", value, "regImg4");
            return (Criteria) this;
        }

        public Criteria andRegImg4NotEqualTo(String value) {
            addCriterion("reg_img4 <>", value, "regImg4");
            return (Criteria) this;
        }

        public Criteria andRegImg4GreaterThan(String value) {
            addCriterion("reg_img4 >", value, "regImg4");
            return (Criteria) this;
        }

        public Criteria andRegImg4GreaterThanOrEqualTo(String value) {
            addCriterion("reg_img4 >=", value, "regImg4");
            return (Criteria) this;
        }

        public Criteria andRegImg4LessThan(String value) {
            addCriterion("reg_img4 <", value, "regImg4");
            return (Criteria) this;
        }

        public Criteria andRegImg4LessThanOrEqualTo(String value) {
            addCriterion("reg_img4 <=", value, "regImg4");
            return (Criteria) this;
        }

        public Criteria andRegImg4Like(String value) {
            addCriterion("reg_img4 like", value, "regImg4");
            return (Criteria) this;
        }

        public Criteria andRegImg4NotLike(String value) {
            addCriterion("reg_img4 not like", value, "regImg4");
            return (Criteria) this;
        }

        public Criteria andRegImg4In(List<String> values) {
            addCriterion("reg_img4 in", values, "regImg4");
            return (Criteria) this;
        }

        public Criteria andRegImg4NotIn(List<String> values) {
            addCriterion("reg_img4 not in", values, "regImg4");
            return (Criteria) this;
        }

        public Criteria andRegImg4Between(String value1, String value2) {
            addCriterion("reg_img4 between", value1, value2, "regImg4");
            return (Criteria) this;
        }

        public Criteria andRegImg4NotBetween(String value1, String value2) {
            addCriterion("reg_img4 not between", value1, value2, "regImg4");
            return (Criteria) this;
        }

        public Criteria andRegImg5IsNull() {
            addCriterion("reg_img5 is null");
            return (Criteria) this;
        }

        public Criteria andRegImg5IsNotNull() {
            addCriterion("reg_img5 is not null");
            return (Criteria) this;
        }

        public Criteria andRegImg5EqualTo(String value) {
            addCriterion("reg_img5 =", value, "regImg5");
            return (Criteria) this;
        }

        public Criteria andRegImg5NotEqualTo(String value) {
            addCriterion("reg_img5 <>", value, "regImg5");
            return (Criteria) this;
        }

        public Criteria andRegImg5GreaterThan(String value) {
            addCriterion("reg_img5 >", value, "regImg5");
            return (Criteria) this;
        }

        public Criteria andRegImg5GreaterThanOrEqualTo(String value) {
            addCriterion("reg_img5 >=", value, "regImg5");
            return (Criteria) this;
        }

        public Criteria andRegImg5LessThan(String value) {
            addCriterion("reg_img5 <", value, "regImg5");
            return (Criteria) this;
        }

        public Criteria andRegImg5LessThanOrEqualTo(String value) {
            addCriterion("reg_img5 <=", value, "regImg5");
            return (Criteria) this;
        }

        public Criteria andRegImg5Like(String value) {
            addCriterion("reg_img5 like", value, "regImg5");
            return (Criteria) this;
        }

        public Criteria andRegImg5NotLike(String value) {
            addCriterion("reg_img5 not like", value, "regImg5");
            return (Criteria) this;
        }

        public Criteria andRegImg5In(List<String> values) {
            addCriterion("reg_img5 in", values, "regImg5");
            return (Criteria) this;
        }

        public Criteria andRegImg5NotIn(List<String> values) {
            addCriterion("reg_img5 not in", values, "regImg5");
            return (Criteria) this;
        }

        public Criteria andRegImg5Between(String value1, String value2) {
            addCriterion("reg_img5 between", value1, value2, "regImg5");
            return (Criteria) this;
        }

        public Criteria andRegImg5NotBetween(String value1, String value2) {
            addCriterion("reg_img5 not between", value1, value2, "regImg5");
            return (Criteria) this;
        }

        public Criteria andRegImg6IsNull() {
            addCriterion("reg_img6 is null");
            return (Criteria) this;
        }

        public Criteria andRegImg6IsNotNull() {
            addCriterion("reg_img6 is not null");
            return (Criteria) this;
        }

        public Criteria andRegImg6EqualTo(String value) {
            addCriterion("reg_img6 =", value, "regImg6");
            return (Criteria) this;
        }

        public Criteria andRegImg6NotEqualTo(String value) {
            addCriterion("reg_img6 <>", value, "regImg6");
            return (Criteria) this;
        }

        public Criteria andRegImg6GreaterThan(String value) {
            addCriterion("reg_img6 >", value, "regImg6");
            return (Criteria) this;
        }

        public Criteria andRegImg6GreaterThanOrEqualTo(String value) {
            addCriterion("reg_img6 >=", value, "regImg6");
            return (Criteria) this;
        }

        public Criteria andRegImg6LessThan(String value) {
            addCriterion("reg_img6 <", value, "regImg6");
            return (Criteria) this;
        }

        public Criteria andRegImg6LessThanOrEqualTo(String value) {
            addCriterion("reg_img6 <=", value, "regImg6");
            return (Criteria) this;
        }

        public Criteria andRegImg6Like(String value) {
            addCriterion("reg_img6 like", value, "regImg6");
            return (Criteria) this;
        }

        public Criteria andRegImg6NotLike(String value) {
            addCriterion("reg_img6 not like", value, "regImg6");
            return (Criteria) this;
        }

        public Criteria andRegImg6In(List<String> values) {
            addCriterion("reg_img6 in", values, "regImg6");
            return (Criteria) this;
        }

        public Criteria andRegImg6NotIn(List<String> values) {
            addCriterion("reg_img6 not in", values, "regImg6");
            return (Criteria) this;
        }

        public Criteria andRegImg6Between(String value1, String value2) {
            addCriterion("reg_img6 between", value1, value2, "regImg6");
            return (Criteria) this;
        }

        public Criteria andRegImg6NotBetween(String value1, String value2) {
            addCriterion("reg_img6 not between", value1, value2, "regImg6");
            return (Criteria) this;
        }

        public Criteria andQqIsNull() {
            addCriterion("QQ is null");
            return (Criteria) this;
        }

        public Criteria andQqIsNotNull() {
            addCriterion("QQ is not null");
            return (Criteria) this;
        }

        public Criteria andQqEqualTo(Integer value) {
            addCriterion("QQ =", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotEqualTo(Integer value) {
            addCriterion("QQ <>", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqGreaterThan(Integer value) {
            addCriterion("QQ >", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqGreaterThanOrEqualTo(Integer value) {
            addCriterion("QQ >=", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLessThan(Integer value) {
            addCriterion("QQ <", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLessThanOrEqualTo(Integer value) {
            addCriterion("QQ <=", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqIn(List<Integer> values) {
            addCriterion("QQ in", values, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotIn(List<Integer> values) {
            addCriterion("QQ not in", values, "qq");
            return (Criteria) this;
        }

        public Criteria andQqBetween(Integer value1, Integer value2) {
            addCriterion("QQ between", value1, value2, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotBetween(Integer value1, Integer value2) {
            addCriterion("QQ not between", value1, value2, "qq");
            return (Criteria) this;
        }

        public Criteria andWeiboIsNull() {
            addCriterion("weibo is null");
            return (Criteria) this;
        }

        public Criteria andWeiboIsNotNull() {
            addCriterion("weibo is not null");
            return (Criteria) this;
        }

        public Criteria andWeiboEqualTo(String value) {
            addCriterion("weibo =", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboNotEqualTo(String value) {
            addCriterion("weibo <>", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboGreaterThan(String value) {
            addCriterion("weibo >", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboGreaterThanOrEqualTo(String value) {
            addCriterion("weibo >=", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboLessThan(String value) {
            addCriterion("weibo <", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboLessThanOrEqualTo(String value) {
            addCriterion("weibo <=", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboLike(String value) {
            addCriterion("weibo like", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboNotLike(String value) {
            addCriterion("weibo not like", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboIn(List<String> values) {
            addCriterion("weibo in", values, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboNotIn(List<String> values) {
            addCriterion("weibo not in", values, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboBetween(String value1, String value2) {
            addCriterion("weibo between", value1, value2, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboNotBetween(String value1, String value2) {
            addCriterion("weibo not between", value1, value2, "weibo");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andWeixinIsNull() {
            addCriterion("weixin is null");
            return (Criteria) this;
        }

        public Criteria andWeixinIsNotNull() {
            addCriterion("weixin is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinEqualTo(String value) {
            addCriterion("weixin =", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotEqualTo(String value) {
            addCriterion("weixin <>", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinGreaterThan(String value) {
            addCriterion("weixin >", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinGreaterThanOrEqualTo(String value) {
            addCriterion("weixin >=", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLessThan(String value) {
            addCriterion("weixin <", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLessThanOrEqualTo(String value) {
            addCriterion("weixin <=", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLike(String value) {
            addCriterion("weixin like", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotLike(String value) {
            addCriterion("weixin not like", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinIn(List<String> values) {
            addCriterion("weixin in", values, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotIn(List<String> values) {
            addCriterion("weixin not in", values, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinBetween(String value1, String value2) {
            addCriterion("weixin between", value1, value2, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotBetween(String value1, String value2) {
            addCriterion("weixin not between", value1, value2, "weixin");
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

        public Criteria andRegMoneyIsNull() {
            addCriterion("reg_money is null");
            return (Criteria) this;
        }

        public Criteria andRegMoneyIsNotNull() {
            addCriterion("reg_money is not null");
            return (Criteria) this;
        }

        public Criteria andRegMoneyEqualTo(String value) {
            addCriterion("reg_money =", value, "regMoney");
            return (Criteria) this;
        }

        public Criteria andRegMoneyNotEqualTo(String value) {
            addCriterion("reg_money <>", value, "regMoney");
            return (Criteria) this;
        }

        public Criteria andRegMoneyGreaterThan(String value) {
            addCriterion("reg_money >", value, "regMoney");
            return (Criteria) this;
        }

        public Criteria andRegMoneyGreaterThanOrEqualTo(String value) {
            addCriterion("reg_money >=", value, "regMoney");
            return (Criteria) this;
        }

        public Criteria andRegMoneyLessThan(String value) {
            addCriterion("reg_money <", value, "regMoney");
            return (Criteria) this;
        }

        public Criteria andRegMoneyLessThanOrEqualTo(String value) {
            addCriterion("reg_money <=", value, "regMoney");
            return (Criteria) this;
        }

        public Criteria andRegMoneyLike(String value) {
            addCriterion("reg_money like", value, "regMoney");
            return (Criteria) this;
        }

        public Criteria andRegMoneyNotLike(String value) {
            addCriterion("reg_money not like", value, "regMoney");
            return (Criteria) this;
        }

        public Criteria andRegMoneyIn(List<String> values) {
            addCriterion("reg_money in", values, "regMoney");
            return (Criteria) this;
        }

        public Criteria andRegMoneyNotIn(List<String> values) {
            addCriterion("reg_money not in", values, "regMoney");
            return (Criteria) this;
        }

        public Criteria andRegMoneyBetween(String value1, String value2) {
            addCriterion("reg_money between", value1, value2, "regMoney");
            return (Criteria) this;
        }

        public Criteria andRegMoneyNotBetween(String value1, String value2) {
            addCriterion("reg_money not between", value1, value2, "regMoney");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andListImgIsNull() {
            addCriterion("list_img is null");
            return (Criteria) this;
        }

        public Criteria andListImgIsNotNull() {
            addCriterion("list_img is not null");
            return (Criteria) this;
        }

        public Criteria andListImgEqualTo(String value) {
            addCriterion("list_img =", value, "listImg");
            return (Criteria) this;
        }

        public Criteria andListImgNotEqualTo(String value) {
            addCriterion("list_img <>", value, "listImg");
            return (Criteria) this;
        }

        public Criteria andListImgGreaterThan(String value) {
            addCriterion("list_img >", value, "listImg");
            return (Criteria) this;
        }

        public Criteria andListImgGreaterThanOrEqualTo(String value) {
            addCriterion("list_img >=", value, "listImg");
            return (Criteria) this;
        }

        public Criteria andListImgLessThan(String value) {
            addCriterion("list_img <", value, "listImg");
            return (Criteria) this;
        }

        public Criteria andListImgLessThanOrEqualTo(String value) {
            addCriterion("list_img <=", value, "listImg");
            return (Criteria) this;
        }

        public Criteria andListImgLike(String value) {
            addCriterion("list_img like", value, "listImg");
            return (Criteria) this;
        }

        public Criteria andListImgNotLike(String value) {
            addCriterion("list_img not like", value, "listImg");
            return (Criteria) this;
        }

        public Criteria andListImgIn(List<String> values) {
            addCriterion("list_img in", values, "listImg");
            return (Criteria) this;
        }

        public Criteria andListImgNotIn(List<String> values) {
            addCriterion("list_img not in", values, "listImg");
            return (Criteria) this;
        }

        public Criteria andListImgBetween(String value1, String value2) {
            addCriterion("list_img between", value1, value2, "listImg");
            return (Criteria) this;
        }

        public Criteria andListImgNotBetween(String value1, String value2) {
            addCriterion("list_img not between", value1, value2, "listImg");
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