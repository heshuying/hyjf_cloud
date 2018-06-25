package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ROaUsersExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public ROaUsersExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andUserLoginIsNull() {
            addCriterion("user_login is null");
            return (Criteria) this;
        }

        public Criteria andUserLoginIsNotNull() {
            addCriterion("user_login is not null");
            return (Criteria) this;
        }

        public Criteria andUserLoginEqualTo(String value) {
            addCriterion("user_login =", value, "userLogin");
            return (Criteria) this;
        }

        public Criteria andUserLoginNotEqualTo(String value) {
            addCriterion("user_login <>", value, "userLogin");
            return (Criteria) this;
        }

        public Criteria andUserLoginGreaterThan(String value) {
            addCriterion("user_login >", value, "userLogin");
            return (Criteria) this;
        }

        public Criteria andUserLoginGreaterThanOrEqualTo(String value) {
            addCriterion("user_login >=", value, "userLogin");
            return (Criteria) this;
        }

        public Criteria andUserLoginLessThan(String value) {
            addCriterion("user_login <", value, "userLogin");
            return (Criteria) this;
        }

        public Criteria andUserLoginLessThanOrEqualTo(String value) {
            addCriterion("user_login <=", value, "userLogin");
            return (Criteria) this;
        }

        public Criteria andUserLoginLike(String value) {
            addCriterion("user_login like", value, "userLogin");
            return (Criteria) this;
        }

        public Criteria andUserLoginNotLike(String value) {
            addCriterion("user_login not like", value, "userLogin");
            return (Criteria) this;
        }

        public Criteria andUserLoginIn(List<String> values) {
            addCriterion("user_login in", values, "userLogin");
            return (Criteria) this;
        }

        public Criteria andUserLoginNotIn(List<String> values) {
            addCriterion("user_login not in", values, "userLogin");
            return (Criteria) this;
        }

        public Criteria andUserLoginBetween(String value1, String value2) {
            addCriterion("user_login between", value1, value2, "userLogin");
            return (Criteria) this;
        }

        public Criteria andUserLoginNotBetween(String value1, String value2) {
            addCriterion("user_login not between", value1, value2, "userLogin");
            return (Criteria) this;
        }

        public Criteria andUserPassIsNull() {
            addCriterion("user_pass is null");
            return (Criteria) this;
        }

        public Criteria andUserPassIsNotNull() {
            addCriterion("user_pass is not null");
            return (Criteria) this;
        }

        public Criteria andUserPassEqualTo(String value) {
            addCriterion("user_pass =", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassNotEqualTo(String value) {
            addCriterion("user_pass <>", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassGreaterThan(String value) {
            addCriterion("user_pass >", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassGreaterThanOrEqualTo(String value) {
            addCriterion("user_pass >=", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassLessThan(String value) {
            addCriterion("user_pass <", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassLessThanOrEqualTo(String value) {
            addCriterion("user_pass <=", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassLike(String value) {
            addCriterion("user_pass like", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassNotLike(String value) {
            addCriterion("user_pass not like", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassIn(List<String> values) {
            addCriterion("user_pass in", values, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassNotIn(List<String> values) {
            addCriterion("user_pass not in", values, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassBetween(String value1, String value2) {
            addCriterion("user_pass between", value1, value2, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassNotBetween(String value1, String value2) {
            addCriterion("user_pass not between", value1, value2, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserRealnameIsNull() {
            addCriterion("user_realname is null");
            return (Criteria) this;
        }

        public Criteria andUserRealnameIsNotNull() {
            addCriterion("user_realname is not null");
            return (Criteria) this;
        }

        public Criteria andUserRealnameEqualTo(String value) {
            addCriterion("user_realname =", value, "userRealname");
            return (Criteria) this;
        }

        public Criteria andUserRealnameNotEqualTo(String value) {
            addCriterion("user_realname <>", value, "userRealname");
            return (Criteria) this;
        }

        public Criteria andUserRealnameGreaterThan(String value) {
            addCriterion("user_realname >", value, "userRealname");
            return (Criteria) this;
        }

        public Criteria andUserRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("user_realname >=", value, "userRealname");
            return (Criteria) this;
        }

        public Criteria andUserRealnameLessThan(String value) {
            addCriterion("user_realname <", value, "userRealname");
            return (Criteria) this;
        }

        public Criteria andUserRealnameLessThanOrEqualTo(String value) {
            addCriterion("user_realname <=", value, "userRealname");
            return (Criteria) this;
        }

        public Criteria andUserRealnameLike(String value) {
            addCriterion("user_realname like", value, "userRealname");
            return (Criteria) this;
        }

        public Criteria andUserRealnameNotLike(String value) {
            addCriterion("user_realname not like", value, "userRealname");
            return (Criteria) this;
        }

        public Criteria andUserRealnameIn(List<String> values) {
            addCriterion("user_realname in", values, "userRealname");
            return (Criteria) this;
        }

        public Criteria andUserRealnameNotIn(List<String> values) {
            addCriterion("user_realname not in", values, "userRealname");
            return (Criteria) this;
        }

        public Criteria andUserRealnameBetween(String value1, String value2) {
            addCriterion("user_realname between", value1, value2, "userRealname");
            return (Criteria) this;
        }

        public Criteria andUserRealnameNotBetween(String value1, String value2) {
            addCriterion("user_realname not between", value1, value2, "userRealname");
            return (Criteria) this;
        }

        public Criteria andUserEmailIsNull() {
            addCriterion("user_email is null");
            return (Criteria) this;
        }

        public Criteria andUserEmailIsNotNull() {
            addCriterion("user_email is not null");
            return (Criteria) this;
        }

        public Criteria andUserEmailEqualTo(String value) {
            addCriterion("user_email =", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotEqualTo(String value) {
            addCriterion("user_email <>", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailGreaterThan(String value) {
            addCriterion("user_email >", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailGreaterThanOrEqualTo(String value) {
            addCriterion("user_email >=", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailLessThan(String value) {
            addCriterion("user_email <", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailLessThanOrEqualTo(String value) {
            addCriterion("user_email <=", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailLike(String value) {
            addCriterion("user_email like", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotLike(String value) {
            addCriterion("user_email not like", value, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailIn(List<String> values) {
            addCriterion("user_email in", values, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotIn(List<String> values) {
            addCriterion("user_email not in", values, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailBetween(String value1, String value2) {
            addCriterion("user_email between", value1, value2, "userEmail");
            return (Criteria) this;
        }

        public Criteria andUserEmailNotBetween(String value1, String value2) {
            addCriterion("user_email not between", value1, value2, "userEmail");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNull() {
            addCriterion("idcard is null");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNotNull() {
            addCriterion("idcard is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardEqualTo(String value) {
            addCriterion("idcard =", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotEqualTo(String value) {
            addCriterion("idcard <>", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThan(String value) {
            addCriterion("idcard >", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("idcard >=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThan(String value) {
            addCriterion("idcard <", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThanOrEqualTo(String value) {
            addCriterion("idcard <=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLike(String value) {
            addCriterion("idcard like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotLike(String value) {
            addCriterion("idcard not like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardIn(List<String> values) {
            addCriterion("idcard in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotIn(List<String> values) {
            addCriterion("idcard not in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardBetween(String value1, String value2) {
            addCriterion("idcard between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotBetween(String value1, String value2) {
            addCriterion("idcard not between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andAvatarIsNull() {
            addCriterion("avatar is null");
            return (Criteria) this;
        }

        public Criteria andAvatarIsNotNull() {
            addCriterion("avatar is not null");
            return (Criteria) this;
        }

        public Criteria andAvatarEqualTo(String value) {
            addCriterion("avatar =", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotEqualTo(String value) {
            addCriterion("avatar <>", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThan(String value) {
            addCriterion("avatar >", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("avatar >=", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLessThan(String value) {
            addCriterion("avatar <", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLessThanOrEqualTo(String value) {
            addCriterion("avatar <=", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarLike(String value) {
            addCriterion("avatar like", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotLike(String value) {
            addCriterion("avatar not like", value, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarIn(List<String> values) {
            addCriterion("avatar in", values, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotIn(List<String> values) {
            addCriterion("avatar not in", values, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarBetween(String value1, String value2) {
            addCriterion("avatar between", value1, value2, "avatar");
            return (Criteria) this;
        }

        public Criteria andAvatarNotBetween(String value1, String value2) {
            addCriterion("avatar not between", value1, value2, "avatar");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Short value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Short value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Short value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Short value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Short value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Short value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Short> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Short> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Short value1, Short value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Short value1, Short value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andAccProvinceIsNull() {
            addCriterion("acc_province is null");
            return (Criteria) this;
        }

        public Criteria andAccProvinceIsNotNull() {
            addCriterion("acc_province is not null");
            return (Criteria) this;
        }

        public Criteria andAccProvinceEqualTo(String value) {
            addCriterion("acc_province =", value, "accProvince");
            return (Criteria) this;
        }

        public Criteria andAccProvinceNotEqualTo(String value) {
            addCriterion("acc_province <>", value, "accProvince");
            return (Criteria) this;
        }

        public Criteria andAccProvinceGreaterThan(String value) {
            addCriterion("acc_province >", value, "accProvince");
            return (Criteria) this;
        }

        public Criteria andAccProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("acc_province >=", value, "accProvince");
            return (Criteria) this;
        }

        public Criteria andAccProvinceLessThan(String value) {
            addCriterion("acc_province <", value, "accProvince");
            return (Criteria) this;
        }

        public Criteria andAccProvinceLessThanOrEqualTo(String value) {
            addCriterion("acc_province <=", value, "accProvince");
            return (Criteria) this;
        }

        public Criteria andAccProvinceLike(String value) {
            addCriterion("acc_province like", value, "accProvince");
            return (Criteria) this;
        }

        public Criteria andAccProvinceNotLike(String value) {
            addCriterion("acc_province not like", value, "accProvince");
            return (Criteria) this;
        }

        public Criteria andAccProvinceIn(List<String> values) {
            addCriterion("acc_province in", values, "accProvince");
            return (Criteria) this;
        }

        public Criteria andAccProvinceNotIn(List<String> values) {
            addCriterion("acc_province not in", values, "accProvince");
            return (Criteria) this;
        }

        public Criteria andAccProvinceBetween(String value1, String value2) {
            addCriterion("acc_province between", value1, value2, "accProvince");
            return (Criteria) this;
        }

        public Criteria andAccProvinceNotBetween(String value1, String value2) {
            addCriterion("acc_province not between", value1, value2, "accProvince");
            return (Criteria) this;
        }

        public Criteria andAccCityIsNull() {
            addCriterion("acc_city is null");
            return (Criteria) this;
        }

        public Criteria andAccCityIsNotNull() {
            addCriterion("acc_city is not null");
            return (Criteria) this;
        }

        public Criteria andAccCityEqualTo(String value) {
            addCriterion("acc_city =", value, "accCity");
            return (Criteria) this;
        }

        public Criteria andAccCityNotEqualTo(String value) {
            addCriterion("acc_city <>", value, "accCity");
            return (Criteria) this;
        }

        public Criteria andAccCityGreaterThan(String value) {
            addCriterion("acc_city >", value, "accCity");
            return (Criteria) this;
        }

        public Criteria andAccCityGreaterThanOrEqualTo(String value) {
            addCriterion("acc_city >=", value, "accCity");
            return (Criteria) this;
        }

        public Criteria andAccCityLessThan(String value) {
            addCriterion("acc_city <", value, "accCity");
            return (Criteria) this;
        }

        public Criteria andAccCityLessThanOrEqualTo(String value) {
            addCriterion("acc_city <=", value, "accCity");
            return (Criteria) this;
        }

        public Criteria andAccCityLike(String value) {
            addCriterion("acc_city like", value, "accCity");
            return (Criteria) this;
        }

        public Criteria andAccCityNotLike(String value) {
            addCriterion("acc_city not like", value, "accCity");
            return (Criteria) this;
        }

        public Criteria andAccCityIn(List<String> values) {
            addCriterion("acc_city in", values, "accCity");
            return (Criteria) this;
        }

        public Criteria andAccCityNotIn(List<String> values) {
            addCriterion("acc_city not in", values, "accCity");
            return (Criteria) this;
        }

        public Criteria andAccCityBetween(String value1, String value2) {
            addCriterion("acc_city between", value1, value2, "accCity");
            return (Criteria) this;
        }

        public Criteria andAccCityNotBetween(String value1, String value2) {
            addCriterion("acc_city not between", value1, value2, "accCity");
            return (Criteria) this;
        }

        public Criteria andAccAddressIsNull() {
            addCriterion("acc_address is null");
            return (Criteria) this;
        }

        public Criteria andAccAddressIsNotNull() {
            addCriterion("acc_address is not null");
            return (Criteria) this;
        }

        public Criteria andAccAddressEqualTo(String value) {
            addCriterion("acc_address =", value, "accAddress");
            return (Criteria) this;
        }

        public Criteria andAccAddressNotEqualTo(String value) {
            addCriterion("acc_address <>", value, "accAddress");
            return (Criteria) this;
        }

        public Criteria andAccAddressGreaterThan(String value) {
            addCriterion("acc_address >", value, "accAddress");
            return (Criteria) this;
        }

        public Criteria andAccAddressGreaterThanOrEqualTo(String value) {
            addCriterion("acc_address >=", value, "accAddress");
            return (Criteria) this;
        }

        public Criteria andAccAddressLessThan(String value) {
            addCriterion("acc_address <", value, "accAddress");
            return (Criteria) this;
        }

        public Criteria andAccAddressLessThanOrEqualTo(String value) {
            addCriterion("acc_address <=", value, "accAddress");
            return (Criteria) this;
        }

        public Criteria andAccAddressLike(String value) {
            addCriterion("acc_address like", value, "accAddress");
            return (Criteria) this;
        }

        public Criteria andAccAddressNotLike(String value) {
            addCriterion("acc_address not like", value, "accAddress");
            return (Criteria) this;
        }

        public Criteria andAccAddressIn(List<String> values) {
            addCriterion("acc_address in", values, "accAddress");
            return (Criteria) this;
        }

        public Criteria andAccAddressNotIn(List<String> values) {
            addCriterion("acc_address not in", values, "accAddress");
            return (Criteria) this;
        }

        public Criteria andAccAddressBetween(String value1, String value2) {
            addCriterion("acc_address between", value1, value2, "accAddress");
            return (Criteria) this;
        }

        public Criteria andAccAddressNotBetween(String value1, String value2) {
            addCriterion("acc_address not between", value1, value2, "accAddress");
            return (Criteria) this;
        }

        public Criteria andDepartmentidIsNull() {
            addCriterion("departmentid is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentidIsNotNull() {
            addCriterion("departmentid is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentidEqualTo(Integer value) {
            addCriterion("departmentid =", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotEqualTo(Integer value) {
            addCriterion("departmentid <>", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidGreaterThan(Integer value) {
            addCriterion("departmentid >", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidGreaterThanOrEqualTo(Integer value) {
            addCriterion("departmentid >=", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidLessThan(Integer value) {
            addCriterion("departmentid <", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidLessThanOrEqualTo(Integer value) {
            addCriterion("departmentid <=", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidIn(List<Integer> values) {
            addCriterion("departmentid in", values, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotIn(List<Integer> values) {
            addCriterion("departmentid not in", values, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidBetween(Integer value1, Integer value2) {
            addCriterion("departmentid between", value1, value2, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotBetween(Integer value1, Integer value2) {
            addCriterion("departmentid not between", value1, value2, "departmentid");
            return (Criteria) this;
        }

        public Criteria andPositionidIsNull() {
            addCriterion("positionid is null");
            return (Criteria) this;
        }

        public Criteria andPositionidIsNotNull() {
            addCriterion("positionid is not null");
            return (Criteria) this;
        }

        public Criteria andPositionidEqualTo(Integer value) {
            addCriterion("positionid =", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidNotEqualTo(Integer value) {
            addCriterion("positionid <>", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidGreaterThan(Integer value) {
            addCriterion("positionid >", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidGreaterThanOrEqualTo(Integer value) {
            addCriterion("positionid >=", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidLessThan(Integer value) {
            addCriterion("positionid <", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidLessThanOrEqualTo(Integer value) {
            addCriterion("positionid <=", value, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidIn(List<Integer> values) {
            addCriterion("positionid in", values, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidNotIn(List<Integer> values) {
            addCriterion("positionid not in", values, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidBetween(Integer value1, Integer value2) {
            addCriterion("positionid between", value1, value2, "positionid");
            return (Criteria) this;
        }

        public Criteria andPositionidNotBetween(Integer value1, Integer value2) {
            addCriterion("positionid not between", value1, value2, "positionid");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("`level` is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("`level` is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Short value) {
            addCriterion("`level` =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Short value) {
            addCriterion("`level` <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Short value) {
            addCriterion("`level` >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Short value) {
            addCriterion("`level` >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Short value) {
            addCriterion("`level` <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Short value) {
            addCriterion("`level` <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Short> values) {
            addCriterion("`level` in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Short> values) {
            addCriterion("`level` not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Short value1, Short value2) {
            addCriterion("`level` between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Short value1, Short value2) {
            addCriterion("`level` not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andTemporaryIsNull() {
            addCriterion("`temporary` is null");
            return (Criteria) this;
        }

        public Criteria andTemporaryIsNotNull() {
            addCriterion("`temporary` is not null");
            return (Criteria) this;
        }

        public Criteria andTemporaryEqualTo(Integer value) {
            addCriterion("`temporary` =", value, "temporary");
            return (Criteria) this;
        }

        public Criteria andTemporaryNotEqualTo(Integer value) {
            addCriterion("`temporary` <>", value, "temporary");
            return (Criteria) this;
        }

        public Criteria andTemporaryGreaterThan(Integer value) {
            addCriterion("`temporary` >", value, "temporary");
            return (Criteria) this;
        }

        public Criteria andTemporaryGreaterThanOrEqualTo(Integer value) {
            addCriterion("`temporary` >=", value, "temporary");
            return (Criteria) this;
        }

        public Criteria andTemporaryLessThan(Integer value) {
            addCriterion("`temporary` <", value, "temporary");
            return (Criteria) this;
        }

        public Criteria andTemporaryLessThanOrEqualTo(Integer value) {
            addCriterion("`temporary` <=", value, "temporary");
            return (Criteria) this;
        }

        public Criteria andTemporaryIn(List<Integer> values) {
            addCriterion("`temporary` in", values, "temporary");
            return (Criteria) this;
        }

        public Criteria andTemporaryNotIn(List<Integer> values) {
            addCriterion("`temporary` not in", values, "temporary");
            return (Criteria) this;
        }

        public Criteria andTemporaryBetween(Integer value1, Integer value2) {
            addCriterion("`temporary` between", value1, value2, "temporary");
            return (Criteria) this;
        }

        public Criteria andTemporaryNotBetween(Integer value1, Integer value2) {
            addCriterion("`temporary` not between", value1, value2, "temporary");
            return (Criteria) this;
        }

        public Criteria andReworkIsNull() {
            addCriterion("rework is null");
            return (Criteria) this;
        }

        public Criteria andReworkIsNotNull() {
            addCriterion("rework is not null");
            return (Criteria) this;
        }

        public Criteria andReworkEqualTo(Integer value) {
            addCriterion("rework =", value, "rework");
            return (Criteria) this;
        }

        public Criteria andReworkNotEqualTo(Integer value) {
            addCriterion("rework <>", value, "rework");
            return (Criteria) this;
        }

        public Criteria andReworkGreaterThan(Integer value) {
            addCriterion("rework >", value, "rework");
            return (Criteria) this;
        }

        public Criteria andReworkGreaterThanOrEqualTo(Integer value) {
            addCriterion("rework >=", value, "rework");
            return (Criteria) this;
        }

        public Criteria andReworkLessThan(Integer value) {
            addCriterion("rework <", value, "rework");
            return (Criteria) this;
        }

        public Criteria andReworkLessThanOrEqualTo(Integer value) {
            addCriterion("rework <=", value, "rework");
            return (Criteria) this;
        }

        public Criteria andReworkIn(List<Integer> values) {
            addCriterion("rework in", values, "rework");
            return (Criteria) this;
        }

        public Criteria andReworkNotIn(List<Integer> values) {
            addCriterion("rework not in", values, "rework");
            return (Criteria) this;
        }

        public Criteria andReworkBetween(Integer value1, Integer value2) {
            addCriterion("rework between", value1, value2, "rework");
            return (Criteria) this;
        }

        public Criteria andReworkNotBetween(Integer value1, Integer value2) {
            addCriterion("rework not between", value1, value2, "rework");
            return (Criteria) this;
        }

        public Criteria andReworkTimeIsNull() {
            addCriterion("rework_time is null");
            return (Criteria) this;
        }

        public Criteria andReworkTimeIsNotNull() {
            addCriterion("rework_time is not null");
            return (Criteria) this;
        }

        public Criteria andReworkTimeEqualTo(String value) {
            addCriterion("rework_time =", value, "reworkTime");
            return (Criteria) this;
        }

        public Criteria andReworkTimeNotEqualTo(String value) {
            addCriterion("rework_time <>", value, "reworkTime");
            return (Criteria) this;
        }

        public Criteria andReworkTimeGreaterThan(String value) {
            addCriterion("rework_time >", value, "reworkTime");
            return (Criteria) this;
        }

        public Criteria andReworkTimeGreaterThanOrEqualTo(String value) {
            addCriterion("rework_time >=", value, "reworkTime");
            return (Criteria) this;
        }

        public Criteria andReworkTimeLessThan(String value) {
            addCriterion("rework_time <", value, "reworkTime");
            return (Criteria) this;
        }

        public Criteria andReworkTimeLessThanOrEqualTo(String value) {
            addCriterion("rework_time <=", value, "reworkTime");
            return (Criteria) this;
        }

        public Criteria andReworkTimeLike(String value) {
            addCriterion("rework_time like", value, "reworkTime");
            return (Criteria) this;
        }

        public Criteria andReworkTimeNotLike(String value) {
            addCriterion("rework_time not like", value, "reworkTime");
            return (Criteria) this;
        }

        public Criteria andReworkTimeIn(List<String> values) {
            addCriterion("rework_time in", values, "reworkTime");
            return (Criteria) this;
        }

        public Criteria andReworkTimeNotIn(List<String> values) {
            addCriterion("rework_time not in", values, "reworkTime");
            return (Criteria) this;
        }

        public Criteria andReworkTimeBetween(String value1, String value2) {
            addCriterion("rework_time between", value1, value2, "reworkTime");
            return (Criteria) this;
        }

        public Criteria andReworkTimeNotBetween(String value1, String value2) {
            addCriterion("rework_time not between", value1, value2, "reworkTime");
            return (Criteria) this;
        }

        public Criteria andIspartIsNull() {
            addCriterion("ispart is null");
            return (Criteria) this;
        }

        public Criteria andIspartIsNotNull() {
            addCriterion("ispart is not null");
            return (Criteria) this;
        }

        public Criteria andIspartEqualTo(String value) {
            addCriterion("ispart =", value, "ispart");
            return (Criteria) this;
        }

        public Criteria andIspartNotEqualTo(String value) {
            addCriterion("ispart <>", value, "ispart");
            return (Criteria) this;
        }

        public Criteria andIspartGreaterThan(String value) {
            addCriterion("ispart >", value, "ispart");
            return (Criteria) this;
        }

        public Criteria andIspartGreaterThanOrEqualTo(String value) {
            addCriterion("ispart >=", value, "ispart");
            return (Criteria) this;
        }

        public Criteria andIspartLessThan(String value) {
            addCriterion("ispart <", value, "ispart");
            return (Criteria) this;
        }

        public Criteria andIspartLessThanOrEqualTo(String value) {
            addCriterion("ispart <=", value, "ispart");
            return (Criteria) this;
        }

        public Criteria andIspartLike(String value) {
            addCriterion("ispart like", value, "ispart");
            return (Criteria) this;
        }

        public Criteria andIspartNotLike(String value) {
            addCriterion("ispart not like", value, "ispart");
            return (Criteria) this;
        }

        public Criteria andIspartIn(List<String> values) {
            addCriterion("ispart in", values, "ispart");
            return (Criteria) this;
        }

        public Criteria andIspartNotIn(List<String> values) {
            addCriterion("ispart not in", values, "ispart");
            return (Criteria) this;
        }

        public Criteria andIspartBetween(String value1, String value2) {
            addCriterion("ispart between", value1, value2, "ispart");
            return (Criteria) this;
        }

        public Criteria andIspartNotBetween(String value1, String value2) {
            addCriterion("ispart not between", value1, value2, "ispart");
            return (Criteria) this;
        }

        public Criteria andPayrollTryIsNull() {
            addCriterion("payroll_try is null");
            return (Criteria) this;
        }

        public Criteria andPayrollTryIsNotNull() {
            addCriterion("payroll_try is not null");
            return (Criteria) this;
        }

        public Criteria andPayrollTryEqualTo(Integer value) {
            addCriterion("payroll_try =", value, "payrollTry");
            return (Criteria) this;
        }

        public Criteria andPayrollTryNotEqualTo(Integer value) {
            addCriterion("payroll_try <>", value, "payrollTry");
            return (Criteria) this;
        }

        public Criteria andPayrollTryGreaterThan(Integer value) {
            addCriterion("payroll_try >", value, "payrollTry");
            return (Criteria) this;
        }

        public Criteria andPayrollTryGreaterThanOrEqualTo(Integer value) {
            addCriterion("payroll_try >=", value, "payrollTry");
            return (Criteria) this;
        }

        public Criteria andPayrollTryLessThan(Integer value) {
            addCriterion("payroll_try <", value, "payrollTry");
            return (Criteria) this;
        }

        public Criteria andPayrollTryLessThanOrEqualTo(Integer value) {
            addCriterion("payroll_try <=", value, "payrollTry");
            return (Criteria) this;
        }

        public Criteria andPayrollTryIn(List<Integer> values) {
            addCriterion("payroll_try in", values, "payrollTry");
            return (Criteria) this;
        }

        public Criteria andPayrollTryNotIn(List<Integer> values) {
            addCriterion("payroll_try not in", values, "payrollTry");
            return (Criteria) this;
        }

        public Criteria andPayrollTryBetween(Integer value1, Integer value2) {
            addCriterion("payroll_try between", value1, value2, "payrollTry");
            return (Criteria) this;
        }

        public Criteria andPayrollTryNotBetween(Integer value1, Integer value2) {
            addCriterion("payroll_try not between", value1, value2, "payrollTry");
            return (Criteria) this;
        }

        public Criteria andPayrollIsNull() {
            addCriterion("payroll is null");
            return (Criteria) this;
        }

        public Criteria andPayrollIsNotNull() {
            addCriterion("payroll is not null");
            return (Criteria) this;
        }

        public Criteria andPayrollEqualTo(Integer value) {
            addCriterion("payroll =", value, "payroll");
            return (Criteria) this;
        }

        public Criteria andPayrollNotEqualTo(Integer value) {
            addCriterion("payroll <>", value, "payroll");
            return (Criteria) this;
        }

        public Criteria andPayrollGreaterThan(Integer value) {
            addCriterion("payroll >", value, "payroll");
            return (Criteria) this;
        }

        public Criteria andPayrollGreaterThanOrEqualTo(Integer value) {
            addCriterion("payroll >=", value, "payroll");
            return (Criteria) this;
        }

        public Criteria andPayrollLessThan(Integer value) {
            addCriterion("payroll <", value, "payroll");
            return (Criteria) this;
        }

        public Criteria andPayrollLessThanOrEqualTo(Integer value) {
            addCriterion("payroll <=", value, "payroll");
            return (Criteria) this;
        }

        public Criteria andPayrollIn(List<Integer> values) {
            addCriterion("payroll in", values, "payroll");
            return (Criteria) this;
        }

        public Criteria andPayrollNotIn(List<Integer> values) {
            addCriterion("payroll not in", values, "payroll");
            return (Criteria) this;
        }

        public Criteria andPayrollBetween(Integer value1, Integer value2) {
            addCriterion("payroll between", value1, value2, "payroll");
            return (Criteria) this;
        }

        public Criteria andPayrollNotBetween(Integer value1, Integer value2) {
            addCriterion("payroll not between", value1, value2, "payroll");
            return (Criteria) this;
        }

        public Criteria andEntrydateIsNull() {
            addCriterion("entrydate is null");
            return (Criteria) this;
        }

        public Criteria andEntrydateIsNotNull() {
            addCriterion("entrydate is not null");
            return (Criteria) this;
        }

        public Criteria andEntrydateEqualTo(Date value) {
            addCriterionForJDBCDate("entrydate =", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateNotEqualTo(Date value) {
            addCriterionForJDBCDate("entrydate <>", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateGreaterThan(Date value) {
            addCriterionForJDBCDate("entrydate >", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("entrydate >=", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateLessThan(Date value) {
            addCriterionForJDBCDate("entrydate <", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("entrydate <=", value, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateIn(List<Date> values) {
            addCriterionForJDBCDate("entrydate in", values, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateNotIn(List<Date> values) {
            addCriterionForJDBCDate("entrydate not in", values, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("entrydate between", value1, value2, "entrydate");
            return (Criteria) this;
        }

        public Criteria andEntrydateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("entrydate not between", value1, value2, "entrydate");
            return (Criteria) this;
        }

        public Criteria andReferenceIsNull() {
            addCriterion("reference is null");
            return (Criteria) this;
        }

        public Criteria andReferenceIsNotNull() {
            addCriterion("reference is not null");
            return (Criteria) this;
        }

        public Criteria andReferenceEqualTo(String value) {
            addCriterion("reference =", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceNotEqualTo(String value) {
            addCriterion("reference <>", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceGreaterThan(String value) {
            addCriterion("reference >", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceGreaterThanOrEqualTo(String value) {
            addCriterion("reference >=", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceLessThan(String value) {
            addCriterion("reference <", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceLessThanOrEqualTo(String value) {
            addCriterion("reference <=", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceLike(String value) {
            addCriterion("reference like", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceNotLike(String value) {
            addCriterion("reference not like", value, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceIn(List<String> values) {
            addCriterion("reference in", values, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceNotIn(List<String> values) {
            addCriterion("reference not in", values, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceBetween(String value1, String value2) {
            addCriterion("reference between", value1, value2, "reference");
            return (Criteria) this;
        }

        public Criteria andReferenceNotBetween(String value1, String value2) {
            addCriterion("reference not between", value1, value2, "reference");
            return (Criteria) this;
        }

        public Criteria andEducationIsNull() {
            addCriterion("education is null");
            return (Criteria) this;
        }

        public Criteria andEducationIsNotNull() {
            addCriterion("education is not null");
            return (Criteria) this;
        }

        public Criteria andEducationEqualTo(Integer value) {
            addCriterion("education =", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotEqualTo(Integer value) {
            addCriterion("education <>", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThan(Integer value) {
            addCriterion("education >", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThanOrEqualTo(Integer value) {
            addCriterion("education >=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThan(Integer value) {
            addCriterion("education <", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThanOrEqualTo(Integer value) {
            addCriterion("education <=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationIn(List<Integer> values) {
            addCriterion("education in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotIn(List<Integer> values) {
            addCriterion("education not in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationBetween(Integer value1, Integer value2) {
            addCriterion("education between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotBetween(Integer value1, Integer value2) {
            addCriterion("education not between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNull() {
            addCriterion("school is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIsNotNull() {
            addCriterion("school is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolEqualTo(String value) {
            addCriterion("school =", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotEqualTo(String value) {
            addCriterion("school <>", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThan(String value) {
            addCriterion("school >", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolGreaterThanOrEqualTo(String value) {
            addCriterion("school >=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThan(String value) {
            addCriterion("school <", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLessThanOrEqualTo(String value) {
            addCriterion("school <=", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolLike(String value) {
            addCriterion("school like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotLike(String value) {
            addCriterion("school not like", value, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolIn(List<String> values) {
            addCriterion("school in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotIn(List<String> values) {
            addCriterion("school not in", values, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolBetween(String value1, String value2) {
            addCriterion("school between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andSchoolNotBetween(String value1, String value2) {
            addCriterion("school not between", value1, value2, "school");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIsNull() {
            addCriterion("specialty is null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIsNotNull() {
            addCriterion("specialty is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialtyEqualTo(String value) {
            addCriterion("specialty =", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNotEqualTo(String value) {
            addCriterion("specialty <>", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyGreaterThan(String value) {
            addCriterion("specialty >", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyGreaterThanOrEqualTo(String value) {
            addCriterion("specialty >=", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyLessThan(String value) {
            addCriterion("specialty <", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyLessThanOrEqualTo(String value) {
            addCriterion("specialty <=", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyLike(String value) {
            addCriterion("specialty like", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNotLike(String value) {
            addCriterion("specialty not like", value, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyIn(List<String> values) {
            addCriterion("specialty in", values, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNotIn(List<String> values) {
            addCriterion("specialty not in", values, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyBetween(String value1, String value2) {
            addCriterion("specialty between", value1, value2, "specialty");
            return (Criteria) this;
        }

        public Criteria andSpecialtyNotBetween(String value1, String value2) {
            addCriterion("specialty not between", value1, value2, "specialty");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpIsNull() {
            addCriterion("last_login_ip is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpIsNotNull() {
            addCriterion("last_login_ip is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpEqualTo(String value) {
            addCriterion("last_login_ip =", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotEqualTo(String value) {
            addCriterion("last_login_ip <>", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpGreaterThan(String value) {
            addCriterion("last_login_ip >", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpGreaterThanOrEqualTo(String value) {
            addCriterion("last_login_ip >=", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLessThan(String value) {
            addCriterion("last_login_ip <", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLessThanOrEqualTo(String value) {
            addCriterion("last_login_ip <=", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpLike(String value) {
            addCriterion("last_login_ip like", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotLike(String value) {
            addCriterion("last_login_ip not like", value, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpIn(List<String> values) {
            addCriterion("last_login_ip in", values, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotIn(List<String> values) {
            addCriterion("last_login_ip not in", values, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpBetween(String value1, String value2) {
            addCriterion("last_login_ip between", value1, value2, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginIpNotBetween(String value1, String value2) {
            addCriterion("last_login_ip not between", value1, value2, "lastLoginIp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNull() {
            addCriterion("last_login_time is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIsNotNull() {
            addCriterion("last_login_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeEqualTo(String value) {
            addCriterion("last_login_time =", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotEqualTo(String value) {
            addCriterion("last_login_time <>", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThan(String value) {
            addCriterion("last_login_time >", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeGreaterThanOrEqualTo(String value) {
            addCriterion("last_login_time >=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThan(String value) {
            addCriterion("last_login_time <", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLessThanOrEqualTo(String value) {
            addCriterion("last_login_time <=", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeLike(String value) {
            addCriterion("last_login_time like", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotLike(String value) {
            addCriterion("last_login_time not like", value, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeIn(List<String> values) {
            addCriterion("last_login_time in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotIn(List<String> values) {
            addCriterion("last_login_time not in", values, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeBetween(String value1, String value2) {
            addCriterion("last_login_time between", value1, value2, "lastLoginTime");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimeNotBetween(String value1, String value2) {
            addCriterion("last_login_time not between", value1, value2, "lastLoginTime");
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

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("create_time like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("create_time not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andBankAddressIsNull() {
            addCriterion("bank_address is null");
            return (Criteria) this;
        }

        public Criteria andBankAddressIsNotNull() {
            addCriterion("bank_address is not null");
            return (Criteria) this;
        }

        public Criteria andBankAddressEqualTo(String value) {
            addCriterion("bank_address =", value, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressNotEqualTo(String value) {
            addCriterion("bank_address <>", value, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressGreaterThan(String value) {
            addCriterion("bank_address >", value, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressGreaterThanOrEqualTo(String value) {
            addCriterion("bank_address >=", value, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressLessThan(String value) {
            addCriterion("bank_address <", value, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressLessThanOrEqualTo(String value) {
            addCriterion("bank_address <=", value, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressLike(String value) {
            addCriterion("bank_address like", value, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressNotLike(String value) {
            addCriterion("bank_address not like", value, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressIn(List<String> values) {
            addCriterion("bank_address in", values, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressNotIn(List<String> values) {
            addCriterion("bank_address not in", values, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressBetween(String value1, String value2) {
            addCriterion("bank_address between", value1, value2, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankAddressNotBetween(String value1, String value2) {
            addCriterion("bank_address not between", value1, value2, "bankAddress");
            return (Criteria) this;
        }

        public Criteria andBankUserIsNull() {
            addCriterion("bank_user is null");
            return (Criteria) this;
        }

        public Criteria andBankUserIsNotNull() {
            addCriterion("bank_user is not null");
            return (Criteria) this;
        }

        public Criteria andBankUserEqualTo(String value) {
            addCriterion("bank_user =", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserNotEqualTo(String value) {
            addCriterion("bank_user <>", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserGreaterThan(String value) {
            addCriterion("bank_user >", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserGreaterThanOrEqualTo(String value) {
            addCriterion("bank_user >=", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserLessThan(String value) {
            addCriterion("bank_user <", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserLessThanOrEqualTo(String value) {
            addCriterion("bank_user <=", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserLike(String value) {
            addCriterion("bank_user like", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserNotLike(String value) {
            addCriterion("bank_user not like", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserIn(List<String> values) {
            addCriterion("bank_user in", values, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserNotIn(List<String> values) {
            addCriterion("bank_user not in", values, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserBetween(String value1, String value2) {
            addCriterion("bank_user between", value1, value2, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserNotBetween(String value1, String value2) {
            addCriterion("bank_user not between", value1, value2, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankNumIsNull() {
            addCriterion("bank_num is null");
            return (Criteria) this;
        }

        public Criteria andBankNumIsNotNull() {
            addCriterion("bank_num is not null");
            return (Criteria) this;
        }

        public Criteria andBankNumEqualTo(String value) {
            addCriterion("bank_num =", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumNotEqualTo(String value) {
            addCriterion("bank_num <>", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumGreaterThan(String value) {
            addCriterion("bank_num >", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumGreaterThanOrEqualTo(String value) {
            addCriterion("bank_num >=", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumLessThan(String value) {
            addCriterion("bank_num <", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumLessThanOrEqualTo(String value) {
            addCriterion("bank_num <=", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumLike(String value) {
            addCriterion("bank_num like", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumNotLike(String value) {
            addCriterion("bank_num not like", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumIn(List<String> values) {
            addCriterion("bank_num in", values, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumNotIn(List<String> values) {
            addCriterion("bank_num not in", values, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumBetween(String value1, String value2) {
            addCriterion("bank_num between", value1, value2, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumNotBetween(String value1, String value2) {
            addCriterion("bank_num not between", value1, value2, "bankNum");
            return (Criteria) this;
        }

        public Criteria andUserStatusIsNull() {
            addCriterion("user_status is null");
            return (Criteria) this;
        }

        public Criteria andUserStatusIsNotNull() {
            addCriterion("user_status is not null");
            return (Criteria) this;
        }

        public Criteria andUserStatusEqualTo(String value) {
            addCriterion("user_status =", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotEqualTo(String value) {
            addCriterion("user_status <>", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusGreaterThan(String value) {
            addCriterion("user_status >", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusGreaterThanOrEqualTo(String value) {
            addCriterion("user_status >=", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusLessThan(String value) {
            addCriterion("user_status <", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusLessThanOrEqualTo(String value) {
            addCriterion("user_status <=", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusLike(String value) {
            addCriterion("user_status like", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotLike(String value) {
            addCriterion("user_status not like", value, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusIn(List<String> values) {
            addCriterion("user_status in", values, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotIn(List<String> values) {
            addCriterion("user_status not in", values, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusBetween(String value1, String value2) {
            addCriterion("user_status between", value1, value2, "userStatus");
            return (Criteria) this;
        }

        public Criteria andUserStatusNotBetween(String value1, String value2) {
            addCriterion("user_status not between", value1, value2, "userStatus");
            return (Criteria) this;
        }

        public Criteria andAgeIsNull() {
            addCriterion("age is null");
            return (Criteria) this;
        }

        public Criteria andAgeIsNotNull() {
            addCriterion("age is not null");
            return (Criteria) this;
        }

        public Criteria andAgeEqualTo(Integer value) {
            addCriterion("age =", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotEqualTo(Integer value) {
            addCriterion("age <>", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThan(Integer value) {
            addCriterion("age >", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThanOrEqualTo(Integer value) {
            addCriterion("age >=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThan(Integer value) {
            addCriterion("age <", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThanOrEqualTo(Integer value) {
            addCriterion("age <=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeIn(List<Integer> values) {
            addCriterion("age in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotIn(List<Integer> values) {
            addCriterion("age not in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeBetween(Integer value1, Integer value2) {
            addCriterion("age between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotBetween(Integer value1, Integer value2) {
            addCriterion("age not between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andHydNameIsNull() {
            addCriterion("hyd_name is null");
            return (Criteria) this;
        }

        public Criteria andHydNameIsNotNull() {
            addCriterion("hyd_name is not null");
            return (Criteria) this;
        }

        public Criteria andHydNameEqualTo(String value) {
            addCriterion("hyd_name =", value, "hydName");
            return (Criteria) this;
        }

        public Criteria andHydNameNotEqualTo(String value) {
            addCriterion("hyd_name <>", value, "hydName");
            return (Criteria) this;
        }

        public Criteria andHydNameGreaterThan(String value) {
            addCriterion("hyd_name >", value, "hydName");
            return (Criteria) this;
        }

        public Criteria andHydNameGreaterThanOrEqualTo(String value) {
            addCriterion("hyd_name >=", value, "hydName");
            return (Criteria) this;
        }

        public Criteria andHydNameLessThan(String value) {
            addCriterion("hyd_name <", value, "hydName");
            return (Criteria) this;
        }

        public Criteria andHydNameLessThanOrEqualTo(String value) {
            addCriterion("hyd_name <=", value, "hydName");
            return (Criteria) this;
        }

        public Criteria andHydNameLike(String value) {
            addCriterion("hyd_name like", value, "hydName");
            return (Criteria) this;
        }

        public Criteria andHydNameNotLike(String value) {
            addCriterion("hyd_name not like", value, "hydName");
            return (Criteria) this;
        }

        public Criteria andHydNameIn(List<String> values) {
            addCriterion("hyd_name in", values, "hydName");
            return (Criteria) this;
        }

        public Criteria andHydNameNotIn(List<String> values) {
            addCriterion("hyd_name not in", values, "hydName");
            return (Criteria) this;
        }

        public Criteria andHydNameBetween(String value1, String value2) {
            addCriterion("hyd_name between", value1, value2, "hydName");
            return (Criteria) this;
        }

        public Criteria andHydNameNotBetween(String value1, String value2) {
            addCriterion("hyd_name not between", value1, value2, "hydName");
            return (Criteria) this;
        }

        public Criteria andHydIdIsNull() {
            addCriterion("hyd_id is null");
            return (Criteria) this;
        }

        public Criteria andHydIdIsNotNull() {
            addCriterion("hyd_id is not null");
            return (Criteria) this;
        }

        public Criteria andHydIdEqualTo(Integer value) {
            addCriterion("hyd_id =", value, "hydId");
            return (Criteria) this;
        }

        public Criteria andHydIdNotEqualTo(Integer value) {
            addCriterion("hyd_id <>", value, "hydId");
            return (Criteria) this;
        }

        public Criteria andHydIdGreaterThan(Integer value) {
            addCriterion("hyd_id >", value, "hydId");
            return (Criteria) this;
        }

        public Criteria andHydIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("hyd_id >=", value, "hydId");
            return (Criteria) this;
        }

        public Criteria andHydIdLessThan(Integer value) {
            addCriterion("hyd_id <", value, "hydId");
            return (Criteria) this;
        }

        public Criteria andHydIdLessThanOrEqualTo(Integer value) {
            addCriterion("hyd_id <=", value, "hydId");
            return (Criteria) this;
        }

        public Criteria andHydIdIn(List<Integer> values) {
            addCriterion("hyd_id in", values, "hydId");
            return (Criteria) this;
        }

        public Criteria andHydIdNotIn(List<Integer> values) {
            addCriterion("hyd_id not in", values, "hydId");
            return (Criteria) this;
        }

        public Criteria andHydIdBetween(Integer value1, Integer value2) {
            addCriterion("hyd_id between", value1, value2, "hydId");
            return (Criteria) this;
        }

        public Criteria andHydIdNotBetween(Integer value1, Integer value2) {
            addCriterion("hyd_id not between", value1, value2, "hydId");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("user_type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("user_type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(Short value) {
            addCriterion("user_type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(Short value) {
            addCriterion("user_type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(Short value) {
            addCriterion("user_type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("user_type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(Short value) {
            addCriterion("user_type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(Short value) {
            addCriterion("user_type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<Short> values) {
            addCriterion("user_type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<Short> values) {
            addCriterion("user_type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(Short value1, Short value2) {
            addCriterion("user_type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(Short value1, Short value2) {
            addCriterion("user_type not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andEntrySuccessTimeIsNull() {
            addCriterion("entry_success_time is null");
            return (Criteria) this;
        }

        public Criteria andEntrySuccessTimeIsNotNull() {
            addCriterion("entry_success_time is not null");
            return (Criteria) this;
        }

        public Criteria andEntrySuccessTimeEqualTo(Integer value) {
            addCriterion("entry_success_time =", value, "entrySuccessTime");
            return (Criteria) this;
        }

        public Criteria andEntrySuccessTimeNotEqualTo(Integer value) {
            addCriterion("entry_success_time <>", value, "entrySuccessTime");
            return (Criteria) this;
        }

        public Criteria andEntrySuccessTimeGreaterThan(Integer value) {
            addCriterion("entry_success_time >", value, "entrySuccessTime");
            return (Criteria) this;
        }

        public Criteria andEntrySuccessTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("entry_success_time >=", value, "entrySuccessTime");
            return (Criteria) this;
        }

        public Criteria andEntrySuccessTimeLessThan(Integer value) {
            addCriterion("entry_success_time <", value, "entrySuccessTime");
            return (Criteria) this;
        }

        public Criteria andEntrySuccessTimeLessThanOrEqualTo(Integer value) {
            addCriterion("entry_success_time <=", value, "entrySuccessTime");
            return (Criteria) this;
        }

        public Criteria andEntrySuccessTimeIn(List<Integer> values) {
            addCriterion("entry_success_time in", values, "entrySuccessTime");
            return (Criteria) this;
        }

        public Criteria andEntrySuccessTimeNotIn(List<Integer> values) {
            addCriterion("entry_success_time not in", values, "entrySuccessTime");
            return (Criteria) this;
        }

        public Criteria andEntrySuccessTimeBetween(Integer value1, Integer value2) {
            addCriterion("entry_success_time between", value1, value2, "entrySuccessTime");
            return (Criteria) this;
        }

        public Criteria andEntrySuccessTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("entry_success_time not between", value1, value2, "entrySuccessTime");
            return (Criteria) this;
        }

        public Criteria andLeaveSuccessTimeIsNull() {
            addCriterion("leave_success_time is null");
            return (Criteria) this;
        }

        public Criteria andLeaveSuccessTimeIsNotNull() {
            addCriterion("leave_success_time is not null");
            return (Criteria) this;
        }

        public Criteria andLeaveSuccessTimeEqualTo(Integer value) {
            addCriterion("leave_success_time =", value, "leaveSuccessTime");
            return (Criteria) this;
        }

        public Criteria andLeaveSuccessTimeNotEqualTo(Integer value) {
            addCriterion("leave_success_time <>", value, "leaveSuccessTime");
            return (Criteria) this;
        }

        public Criteria andLeaveSuccessTimeGreaterThan(Integer value) {
            addCriterion("leave_success_time >", value, "leaveSuccessTime");
            return (Criteria) this;
        }

        public Criteria andLeaveSuccessTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("leave_success_time >=", value, "leaveSuccessTime");
            return (Criteria) this;
        }

        public Criteria andLeaveSuccessTimeLessThan(Integer value) {
            addCriterion("leave_success_time <", value, "leaveSuccessTime");
            return (Criteria) this;
        }

        public Criteria andLeaveSuccessTimeLessThanOrEqualTo(Integer value) {
            addCriterion("leave_success_time <=", value, "leaveSuccessTime");
            return (Criteria) this;
        }

        public Criteria andLeaveSuccessTimeIn(List<Integer> values) {
            addCriterion("leave_success_time in", values, "leaveSuccessTime");
            return (Criteria) this;
        }

        public Criteria andLeaveSuccessTimeNotIn(List<Integer> values) {
            addCriterion("leave_success_time not in", values, "leaveSuccessTime");
            return (Criteria) this;
        }

        public Criteria andLeaveSuccessTimeBetween(Integer value1, Integer value2) {
            addCriterion("leave_success_time between", value1, value2, "leaveSuccessTime");
            return (Criteria) this;
        }

        public Criteria andLeaveSuccessTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("leave_success_time not between", value1, value2, "leaveSuccessTime");
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