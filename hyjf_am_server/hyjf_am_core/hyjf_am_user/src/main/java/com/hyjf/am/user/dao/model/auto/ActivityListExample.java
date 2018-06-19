package com.hyjf.am.user.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class ActivityListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public ActivityListExample() {
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTimeStartIsNull() {
            addCriterion("time_start is null");
            return (Criteria) this;
        }

        public Criteria andTimeStartIsNotNull() {
            addCriterion("time_start is not null");
            return (Criteria) this;
        }

        public Criteria andTimeStartEqualTo(Integer value) {
            addCriterion("time_start =", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartNotEqualTo(Integer value) {
            addCriterion("time_start <>", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartGreaterThan(Integer value) {
            addCriterion("time_start >", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartGreaterThanOrEqualTo(Integer value) {
            addCriterion("time_start >=", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartLessThan(Integer value) {
            addCriterion("time_start <", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartLessThanOrEqualTo(Integer value) {
            addCriterion("time_start <=", value, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartIn(List<Integer> values) {
            addCriterion("time_start in", values, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartNotIn(List<Integer> values) {
            addCriterion("time_start not in", values, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartBetween(Integer value1, Integer value2) {
            addCriterion("time_start between", value1, value2, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeStartNotBetween(Integer value1, Integer value2) {
            addCriterion("time_start not between", value1, value2, "timeStart");
            return (Criteria) this;
        }

        public Criteria andTimeEndIsNull() {
            addCriterion("time_end is null");
            return (Criteria) this;
        }

        public Criteria andTimeEndIsNotNull() {
            addCriterion("time_end is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEndEqualTo(Integer value) {
            addCriterion("time_end =", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndNotEqualTo(Integer value) {
            addCriterion("time_end <>", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndGreaterThan(Integer value) {
            addCriterion("time_end >", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndGreaterThanOrEqualTo(Integer value) {
            addCriterion("time_end >=", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndLessThan(Integer value) {
            addCriterion("time_end <", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndLessThanOrEqualTo(Integer value) {
            addCriterion("time_end <=", value, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndIn(List<Integer> values) {
            addCriterion("time_end in", values, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndNotIn(List<Integer> values) {
            addCriterion("time_end not in", values, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndBetween(Integer value1, Integer value2) {
            addCriterion("time_end between", value1, value2, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andTimeEndNotBetween(Integer value1, Integer value2) {
            addCriterion("time_end not between", value1, value2, "timeEnd");
            return (Criteria) this;
        }

        public Criteria andImgPcIsNull() {
            addCriterion("img_pc is null");
            return (Criteria) this;
        }

        public Criteria andImgPcIsNotNull() {
            addCriterion("img_pc is not null");
            return (Criteria) this;
        }

        public Criteria andImgPcEqualTo(String value) {
            addCriterion("img_pc =", value, "imgPc");
            return (Criteria) this;
        }

        public Criteria andImgPcNotEqualTo(String value) {
            addCriterion("img_pc <>", value, "imgPc");
            return (Criteria) this;
        }

        public Criteria andImgPcGreaterThan(String value) {
            addCriterion("img_pc >", value, "imgPc");
            return (Criteria) this;
        }

        public Criteria andImgPcGreaterThanOrEqualTo(String value) {
            addCriterion("img_pc >=", value, "imgPc");
            return (Criteria) this;
        }

        public Criteria andImgPcLessThan(String value) {
            addCriterion("img_pc <", value, "imgPc");
            return (Criteria) this;
        }

        public Criteria andImgPcLessThanOrEqualTo(String value) {
            addCriterion("img_pc <=", value, "imgPc");
            return (Criteria) this;
        }

        public Criteria andImgPcLike(String value) {
            addCriterion("img_pc like", value, "imgPc");
            return (Criteria) this;
        }

        public Criteria andImgPcNotLike(String value) {
            addCriterion("img_pc not like", value, "imgPc");
            return (Criteria) this;
        }

        public Criteria andImgPcIn(List<String> values) {
            addCriterion("img_pc in", values, "imgPc");
            return (Criteria) this;
        }

        public Criteria andImgPcNotIn(List<String> values) {
            addCriterion("img_pc not in", values, "imgPc");
            return (Criteria) this;
        }

        public Criteria andImgPcBetween(String value1, String value2) {
            addCriterion("img_pc between", value1, value2, "imgPc");
            return (Criteria) this;
        }

        public Criteria andImgPcNotBetween(String value1, String value2) {
            addCriterion("img_pc not between", value1, value2, "imgPc");
            return (Criteria) this;
        }

        public Criteria andImgAppIsNull() {
            addCriterion("img_app is null");
            return (Criteria) this;
        }

        public Criteria andImgAppIsNotNull() {
            addCriterion("img_app is not null");
            return (Criteria) this;
        }

        public Criteria andImgAppEqualTo(String value) {
            addCriterion("img_app =", value, "imgApp");
            return (Criteria) this;
        }

        public Criteria andImgAppNotEqualTo(String value) {
            addCriterion("img_app <>", value, "imgApp");
            return (Criteria) this;
        }

        public Criteria andImgAppGreaterThan(String value) {
            addCriterion("img_app >", value, "imgApp");
            return (Criteria) this;
        }

        public Criteria andImgAppGreaterThanOrEqualTo(String value) {
            addCriterion("img_app >=", value, "imgApp");
            return (Criteria) this;
        }

        public Criteria andImgAppLessThan(String value) {
            addCriterion("img_app <", value, "imgApp");
            return (Criteria) this;
        }

        public Criteria andImgAppLessThanOrEqualTo(String value) {
            addCriterion("img_app <=", value, "imgApp");
            return (Criteria) this;
        }

        public Criteria andImgAppLike(String value) {
            addCriterion("img_app like", value, "imgApp");
            return (Criteria) this;
        }

        public Criteria andImgAppNotLike(String value) {
            addCriterion("img_app not like", value, "imgApp");
            return (Criteria) this;
        }

        public Criteria andImgAppIn(List<String> values) {
            addCriterion("img_app in", values, "imgApp");
            return (Criteria) this;
        }

        public Criteria andImgAppNotIn(List<String> values) {
            addCriterion("img_app not in", values, "imgApp");
            return (Criteria) this;
        }

        public Criteria andImgAppBetween(String value1, String value2) {
            addCriterion("img_app between", value1, value2, "imgApp");
            return (Criteria) this;
        }

        public Criteria andImgAppNotBetween(String value1, String value2) {
            addCriterion("img_app not between", value1, value2, "imgApp");
            return (Criteria) this;
        }

        public Criteria andImgWeiIsNull() {
            addCriterion("img_wei is null");
            return (Criteria) this;
        }

        public Criteria andImgWeiIsNotNull() {
            addCriterion("img_wei is not null");
            return (Criteria) this;
        }

        public Criteria andImgWeiEqualTo(String value) {
            addCriterion("img_wei =", value, "imgWei");
            return (Criteria) this;
        }

        public Criteria andImgWeiNotEqualTo(String value) {
            addCriterion("img_wei <>", value, "imgWei");
            return (Criteria) this;
        }

        public Criteria andImgWeiGreaterThan(String value) {
            addCriterion("img_wei >", value, "imgWei");
            return (Criteria) this;
        }

        public Criteria andImgWeiGreaterThanOrEqualTo(String value) {
            addCriterion("img_wei >=", value, "imgWei");
            return (Criteria) this;
        }

        public Criteria andImgWeiLessThan(String value) {
            addCriterion("img_wei <", value, "imgWei");
            return (Criteria) this;
        }

        public Criteria andImgWeiLessThanOrEqualTo(String value) {
            addCriterion("img_wei <=", value, "imgWei");
            return (Criteria) this;
        }

        public Criteria andImgWeiLike(String value) {
            addCriterion("img_wei like", value, "imgWei");
            return (Criteria) this;
        }

        public Criteria andImgWeiNotLike(String value) {
            addCriterion("img_wei not like", value, "imgWei");
            return (Criteria) this;
        }

        public Criteria andImgWeiIn(List<String> values) {
            addCriterion("img_wei in", values, "imgWei");
            return (Criteria) this;
        }

        public Criteria andImgWeiNotIn(List<String> values) {
            addCriterion("img_wei not in", values, "imgWei");
            return (Criteria) this;
        }

        public Criteria andImgWeiBetween(String value1, String value2) {
            addCriterion("img_wei between", value1, value2, "imgWei");
            return (Criteria) this;
        }

        public Criteria andImgWeiNotBetween(String value1, String value2) {
            addCriterion("img_wei not between", value1, value2, "imgWei");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlIsNull() {
            addCriterion("activity_pc_url is null");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlIsNotNull() {
            addCriterion("activity_pc_url is not null");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlEqualTo(String value) {
            addCriterion("activity_pc_url =", value, "activityPcUrl");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlNotEqualTo(String value) {
            addCriterion("activity_pc_url <>", value, "activityPcUrl");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlGreaterThan(String value) {
            addCriterion("activity_pc_url >", value, "activityPcUrl");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlGreaterThanOrEqualTo(String value) {
            addCriterion("activity_pc_url >=", value, "activityPcUrl");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlLessThan(String value) {
            addCriterion("activity_pc_url <", value, "activityPcUrl");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlLessThanOrEqualTo(String value) {
            addCriterion("activity_pc_url <=", value, "activityPcUrl");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlLike(String value) {
            addCriterion("activity_pc_url like", value, "activityPcUrl");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlNotLike(String value) {
            addCriterion("activity_pc_url not like", value, "activityPcUrl");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlIn(List<String> values) {
            addCriterion("activity_pc_url in", values, "activityPcUrl");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlNotIn(List<String> values) {
            addCriterion("activity_pc_url not in", values, "activityPcUrl");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlBetween(String value1, String value2) {
            addCriterion("activity_pc_url between", value1, value2, "activityPcUrl");
            return (Criteria) this;
        }

        public Criteria andActivityPcUrlNotBetween(String value1, String value2) {
            addCriterion("activity_pc_url not between", value1, value2, "activityPcUrl");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlIsNull() {
            addCriterion("activity_app_url is null");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlIsNotNull() {
            addCriterion("activity_app_url is not null");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlEqualTo(String value) {
            addCriterion("activity_app_url =", value, "activityAppUrl");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlNotEqualTo(String value) {
            addCriterion("activity_app_url <>", value, "activityAppUrl");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlGreaterThan(String value) {
            addCriterion("activity_app_url >", value, "activityAppUrl");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlGreaterThanOrEqualTo(String value) {
            addCriterion("activity_app_url >=", value, "activityAppUrl");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlLessThan(String value) {
            addCriterion("activity_app_url <", value, "activityAppUrl");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlLessThanOrEqualTo(String value) {
            addCriterion("activity_app_url <=", value, "activityAppUrl");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlLike(String value) {
            addCriterion("activity_app_url like", value, "activityAppUrl");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlNotLike(String value) {
            addCriterion("activity_app_url not like", value, "activityAppUrl");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlIn(List<String> values) {
            addCriterion("activity_app_url in", values, "activityAppUrl");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlNotIn(List<String> values) {
            addCriterion("activity_app_url not in", values, "activityAppUrl");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlBetween(String value1, String value2) {
            addCriterion("activity_app_url between", value1, value2, "activityAppUrl");
            return (Criteria) this;
        }

        public Criteria andActivityAppUrlNotBetween(String value1, String value2) {
            addCriterion("activity_app_url not between", value1, value2, "activityAppUrl");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlIsNull() {
            addCriterion("activity_wei_url is null");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlIsNotNull() {
            addCriterion("activity_wei_url is not null");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlEqualTo(String value) {
            addCriterion("activity_wei_url =", value, "activityWeiUrl");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlNotEqualTo(String value) {
            addCriterion("activity_wei_url <>", value, "activityWeiUrl");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlGreaterThan(String value) {
            addCriterion("activity_wei_url >", value, "activityWeiUrl");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlGreaterThanOrEqualTo(String value) {
            addCriterion("activity_wei_url >=", value, "activityWeiUrl");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlLessThan(String value) {
            addCriterion("activity_wei_url <", value, "activityWeiUrl");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlLessThanOrEqualTo(String value) {
            addCriterion("activity_wei_url <=", value, "activityWeiUrl");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlLike(String value) {
            addCriterion("activity_wei_url like", value, "activityWeiUrl");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlNotLike(String value) {
            addCriterion("activity_wei_url not like", value, "activityWeiUrl");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlIn(List<String> values) {
            addCriterion("activity_wei_url in", values, "activityWeiUrl");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlNotIn(List<String> values) {
            addCriterion("activity_wei_url not in", values, "activityWeiUrl");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlBetween(String value1, String value2) {
            addCriterion("activity_wei_url between", value1, value2, "activityWeiUrl");
            return (Criteria) this;
        }

        public Criteria andActivityWeiUrlNotBetween(String value1, String value2) {
            addCriterion("activity_wei_url not between", value1, value2, "activityWeiUrl");
            return (Criteria) this;
        }

        public Criteria andImgIsNull() {
            addCriterion("img is null");
            return (Criteria) this;
        }

        public Criteria andImgIsNotNull() {
            addCriterion("img is not null");
            return (Criteria) this;
        }

        public Criteria andImgEqualTo(String value) {
            addCriterion("img =", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotEqualTo(String value) {
            addCriterion("img <>", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgGreaterThan(String value) {
            addCriterion("img >", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgGreaterThanOrEqualTo(String value) {
            addCriterion("img >=", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgLessThan(String value) {
            addCriterion("img <", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgLessThanOrEqualTo(String value) {
            addCriterion("img <=", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgLike(String value) {
            addCriterion("img like", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotLike(String value) {
            addCriterion("img not like", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgIn(List<String> values) {
            addCriterion("img in", values, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotIn(List<String> values) {
            addCriterion("img not in", values, "img");
            return (Criteria) this;
        }

        public Criteria andImgBetween(String value1, String value2) {
            addCriterion("img between", value1, value2, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotBetween(String value1, String value2) {
            addCriterion("img not between", value1, value2, "img");
            return (Criteria) this;
        }

        public Criteria andQrIsNull() {
            addCriterion("qr is null");
            return (Criteria) this;
        }

        public Criteria andQrIsNotNull() {
            addCriterion("qr is not null");
            return (Criteria) this;
        }

        public Criteria andQrEqualTo(String value) {
            addCriterion("qr =", value, "qr");
            return (Criteria) this;
        }

        public Criteria andQrNotEqualTo(String value) {
            addCriterion("qr <>", value, "qr");
            return (Criteria) this;
        }

        public Criteria andQrGreaterThan(String value) {
            addCriterion("qr >", value, "qr");
            return (Criteria) this;
        }

        public Criteria andQrGreaterThanOrEqualTo(String value) {
            addCriterion("qr >=", value, "qr");
            return (Criteria) this;
        }

        public Criteria andQrLessThan(String value) {
            addCriterion("qr <", value, "qr");
            return (Criteria) this;
        }

        public Criteria andQrLessThanOrEqualTo(String value) {
            addCriterion("qr <=", value, "qr");
            return (Criteria) this;
        }

        public Criteria andQrLike(String value) {
            addCriterion("qr like", value, "qr");
            return (Criteria) this;
        }

        public Criteria andQrNotLike(String value) {
            addCriterion("qr not like", value, "qr");
            return (Criteria) this;
        }

        public Criteria andQrIn(List<String> values) {
            addCriterion("qr in", values, "qr");
            return (Criteria) this;
        }

        public Criteria andQrNotIn(List<String> values) {
            addCriterion("qr not in", values, "qr");
            return (Criteria) this;
        }

        public Criteria andQrBetween(String value1, String value2) {
            addCriterion("qr between", value1, value2, "qr");
            return (Criteria) this;
        }

        public Criteria andQrNotBetween(String value1, String value2) {
            addCriterion("qr not between", value1, value2, "qr");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNull() {
            addCriterion("platform is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNotNull() {
            addCriterion("platform is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformEqualTo(String value) {
            addCriterion("platform =", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotEqualTo(String value) {
            addCriterion("platform <>", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThan(String value) {
            addCriterion("platform >", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("platform >=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThan(String value) {
            addCriterion("platform <", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThanOrEqualTo(String value) {
            addCriterion("platform <=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLike(String value) {
            addCriterion("platform like", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotLike(String value) {
            addCriterion("platform not like", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformIn(List<String> values) {
            addCriterion("platform in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotIn(List<String> values) {
            addCriterion("platform not in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformBetween(String value1, String value2) {
            addCriterion("platform between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotBetween(String value1, String value2) {
            addCriterion("platform not between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundIsNull() {
            addCriterion("url_foreground is null");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundIsNotNull() {
            addCriterion("url_foreground is not null");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundEqualTo(String value) {
            addCriterion("url_foreground =", value, "urlForeground");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundNotEqualTo(String value) {
            addCriterion("url_foreground <>", value, "urlForeground");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundGreaterThan(String value) {
            addCriterion("url_foreground >", value, "urlForeground");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundGreaterThanOrEqualTo(String value) {
            addCriterion("url_foreground >=", value, "urlForeground");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundLessThan(String value) {
            addCriterion("url_foreground <", value, "urlForeground");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundLessThanOrEqualTo(String value) {
            addCriterion("url_foreground <=", value, "urlForeground");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundLike(String value) {
            addCriterion("url_foreground like", value, "urlForeground");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundNotLike(String value) {
            addCriterion("url_foreground not like", value, "urlForeground");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundIn(List<String> values) {
            addCriterion("url_foreground in", values, "urlForeground");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundNotIn(List<String> values) {
            addCriterion("url_foreground not in", values, "urlForeground");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundBetween(String value1, String value2) {
            addCriterion("url_foreground between", value1, value2, "urlForeground");
            return (Criteria) this;
        }

        public Criteria andUrlForegroundNotBetween(String value1, String value2) {
            addCriterion("url_foreground not between", value1, value2, "urlForeground");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundIsNull() {
            addCriterion("url_background is null");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundIsNotNull() {
            addCriterion("url_background is not null");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundEqualTo(String value) {
            addCriterion("url_background =", value, "urlBackground");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundNotEqualTo(String value) {
            addCriterion("url_background <>", value, "urlBackground");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundGreaterThan(String value) {
            addCriterion("url_background >", value, "urlBackground");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundGreaterThanOrEqualTo(String value) {
            addCriterion("url_background >=", value, "urlBackground");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundLessThan(String value) {
            addCriterion("url_background <", value, "urlBackground");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundLessThanOrEqualTo(String value) {
            addCriterion("url_background <=", value, "urlBackground");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundLike(String value) {
            addCriterion("url_background like", value, "urlBackground");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundNotLike(String value) {
            addCriterion("url_background not like", value, "urlBackground");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundIn(List<String> values) {
            addCriterion("url_background in", values, "urlBackground");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundNotIn(List<String> values) {
            addCriterion("url_background not in", values, "urlBackground");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundBetween(String value1, String value2) {
            addCriterion("url_background between", value1, value2, "urlBackground");
            return (Criteria) this;
        }

        public Criteria andUrlBackgroundNotBetween(String value1, String value2) {
            addCriterion("url_background not between", value1, value2, "urlBackground");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
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

        public Criteria andCreateTimeEqualTo(Integer value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Integer value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Integer value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Integer value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Integer> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Integer> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Integer value1, Integer value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Integer value1, Integer value2) {
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

        public Criteria andUpdateTimeEqualTo(Integer value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Integer value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Integer value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Integer value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Integer> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Integer> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Integer value1, Integer value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Integer value1, Integer value2) {
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