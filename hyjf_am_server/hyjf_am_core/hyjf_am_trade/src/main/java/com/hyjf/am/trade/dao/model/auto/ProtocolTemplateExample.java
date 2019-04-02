package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProtocolTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public ProtocolTemplateExample() {
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

        public Criteria andProtocolIdIsNull() {
            addCriterion("protocol_id is null");
            return (Criteria) this;
        }

        public Criteria andProtocolIdIsNotNull() {
            addCriterion("protocol_id is not null");
            return (Criteria) this;
        }

        public Criteria andProtocolIdEqualTo(String value) {
            addCriterion("protocol_id =", value, "protocolId");
            return (Criteria) this;
        }

        public Criteria andProtocolIdNotEqualTo(String value) {
            addCriterion("protocol_id <>", value, "protocolId");
            return (Criteria) this;
        }

        public Criteria andProtocolIdGreaterThan(String value) {
            addCriterion("protocol_id >", value, "protocolId");
            return (Criteria) this;
        }

        public Criteria andProtocolIdGreaterThanOrEqualTo(String value) {
            addCriterion("protocol_id >=", value, "protocolId");
            return (Criteria) this;
        }

        public Criteria andProtocolIdLessThan(String value) {
            addCriterion("protocol_id <", value, "protocolId");
            return (Criteria) this;
        }

        public Criteria andProtocolIdLessThanOrEqualTo(String value) {
            addCriterion("protocol_id <=", value, "protocolId");
            return (Criteria) this;
        }

        public Criteria andProtocolIdLike(String value) {
            addCriterion("protocol_id like", value, "protocolId");
            return (Criteria) this;
        }

        public Criteria andProtocolIdNotLike(String value) {
            addCriterion("protocol_id not like", value, "protocolId");
            return (Criteria) this;
        }

        public Criteria andProtocolIdIn(List<String> values) {
            addCriterion("protocol_id in", values, "protocolId");
            return (Criteria) this;
        }

        public Criteria andProtocolIdNotIn(List<String> values) {
            addCriterion("protocol_id not in", values, "protocolId");
            return (Criteria) this;
        }

        public Criteria andProtocolIdBetween(String value1, String value2) {
            addCriterion("protocol_id between", value1, value2, "protocolId");
            return (Criteria) this;
        }

        public Criteria andProtocolIdNotBetween(String value1, String value2) {
            addCriterion("protocol_id not between", value1, value2, "protocolId");
            return (Criteria) this;
        }

        public Criteria andProtocolNameIsNull() {
            addCriterion("protocol_name is null");
            return (Criteria) this;
        }

        public Criteria andProtocolNameIsNotNull() {
            addCriterion("protocol_name is not null");
            return (Criteria) this;
        }

        public Criteria andProtocolNameEqualTo(String value) {
            addCriterion("protocol_name =", value, "protocolName");
            return (Criteria) this;
        }

        public Criteria andProtocolNameNotEqualTo(String value) {
            addCriterion("protocol_name <>", value, "protocolName");
            return (Criteria) this;
        }

        public Criteria andProtocolNameGreaterThan(String value) {
            addCriterion("protocol_name >", value, "protocolName");
            return (Criteria) this;
        }

        public Criteria andProtocolNameGreaterThanOrEqualTo(String value) {
            addCriterion("protocol_name >=", value, "protocolName");
            return (Criteria) this;
        }

        public Criteria andProtocolNameLessThan(String value) {
            addCriterion("protocol_name <", value, "protocolName");
            return (Criteria) this;
        }

        public Criteria andProtocolNameLessThanOrEqualTo(String value) {
            addCriterion("protocol_name <=", value, "protocolName");
            return (Criteria) this;
        }

        public Criteria andProtocolNameLike(String value) {
            addCriterion("protocol_name like", value, "protocolName");
            return (Criteria) this;
        }

        public Criteria andProtocolNameNotLike(String value) {
            addCriterion("protocol_name not like", value, "protocolName");
            return (Criteria) this;
        }

        public Criteria andProtocolNameIn(List<String> values) {
            addCriterion("protocol_name in", values, "protocolName");
            return (Criteria) this;
        }

        public Criteria andProtocolNameNotIn(List<String> values) {
            addCriterion("protocol_name not in", values, "protocolName");
            return (Criteria) this;
        }

        public Criteria andProtocolNameBetween(String value1, String value2) {
            addCriterion("protocol_name between", value1, value2, "protocolName");
            return (Criteria) this;
        }

        public Criteria andProtocolNameNotBetween(String value1, String value2) {
            addCriterion("protocol_name not between", value1, value2, "protocolName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameIsNull() {
            addCriterion("display_name is null");
            return (Criteria) this;
        }

        public Criteria andDisplayNameIsNotNull() {
            addCriterion("display_name is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayNameEqualTo(String value) {
            addCriterion("display_name =", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameNotEqualTo(String value) {
            addCriterion("display_name <>", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameGreaterThan(String value) {
            addCriterion("display_name >", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameGreaterThanOrEqualTo(String value) {
            addCriterion("display_name >=", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameLessThan(String value) {
            addCriterion("display_name <", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameLessThanOrEqualTo(String value) {
            addCriterion("display_name <=", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameLike(String value) {
            addCriterion("display_name like", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameNotLike(String value) {
            addCriterion("display_name not like", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameIn(List<String> values) {
            addCriterion("display_name in", values, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameNotIn(List<String> values) {
            addCriterion("display_name not in", values, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameBetween(String value1, String value2) {
            addCriterion("display_name between", value1, value2, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameNotBetween(String value1, String value2) {
            addCriterion("display_name not between", value1, value2, "displayName");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeIsNull() {
            addCriterion("protocol_type is null");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeIsNotNull() {
            addCriterion("protocol_type is not null");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeEqualTo(String value) {
            addCriterion("protocol_type =", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeNotEqualTo(String value) {
            addCriterion("protocol_type <>", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeGreaterThan(String value) {
            addCriterion("protocol_type >", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeGreaterThanOrEqualTo(String value) {
            addCriterion("protocol_type >=", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeLessThan(String value) {
            addCriterion("protocol_type <", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeLessThanOrEqualTo(String value) {
            addCriterion("protocol_type <=", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeLike(String value) {
            addCriterion("protocol_type like", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeNotLike(String value) {
            addCriterion("protocol_type not like", value, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeIn(List<String> values) {
            addCriterion("protocol_type in", values, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeNotIn(List<String> values) {
            addCriterion("protocol_type not in", values, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeBetween(String value1, String value2) {
            addCriterion("protocol_type between", value1, value2, "protocolType");
            return (Criteria) this;
        }

        public Criteria andProtocolTypeNotBetween(String value1, String value2) {
            addCriterion("protocol_type not between", value1, value2, "protocolType");
            return (Criteria) this;
        }

        public Criteria andVersionNumberIsNull() {
            addCriterion("version_number is null");
            return (Criteria) this;
        }

        public Criteria andVersionNumberIsNotNull() {
            addCriterion("version_number is not null");
            return (Criteria) this;
        }

        public Criteria andVersionNumberEqualTo(String value) {
            addCriterion("version_number =", value, "versionNumber");
            return (Criteria) this;
        }

        public Criteria andVersionNumberNotEqualTo(String value) {
            addCriterion("version_number <>", value, "versionNumber");
            return (Criteria) this;
        }

        public Criteria andVersionNumberGreaterThan(String value) {
            addCriterion("version_number >", value, "versionNumber");
            return (Criteria) this;
        }

        public Criteria andVersionNumberGreaterThanOrEqualTo(String value) {
            addCriterion("version_number >=", value, "versionNumber");
            return (Criteria) this;
        }

        public Criteria andVersionNumberLessThan(String value) {
            addCriterion("version_number <", value, "versionNumber");
            return (Criteria) this;
        }

        public Criteria andVersionNumberLessThanOrEqualTo(String value) {
            addCriterion("version_number <=", value, "versionNumber");
            return (Criteria) this;
        }

        public Criteria andVersionNumberLike(String value) {
            addCriterion("version_number like", value, "versionNumber");
            return (Criteria) this;
        }

        public Criteria andVersionNumberNotLike(String value) {
            addCriterion("version_number not like", value, "versionNumber");
            return (Criteria) this;
        }

        public Criteria andVersionNumberIn(List<String> values) {
            addCriterion("version_number in", values, "versionNumber");
            return (Criteria) this;
        }

        public Criteria andVersionNumberNotIn(List<String> values) {
            addCriterion("version_number not in", values, "versionNumber");
            return (Criteria) this;
        }

        public Criteria andVersionNumberBetween(String value1, String value2) {
            addCriterion("version_number between", value1, value2, "versionNumber");
            return (Criteria) this;
        }

        public Criteria andVersionNumberNotBetween(String value1, String value2) {
            addCriterion("version_number not between", value1, value2, "versionNumber");
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

        public Criteria andProtocolUrlIsNull() {
            addCriterion("protocol_url is null");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlIsNotNull() {
            addCriterion("protocol_url is not null");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlEqualTo(String value) {
            addCriterion("protocol_url =", value, "protocolUrl");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlNotEqualTo(String value) {
            addCriterion("protocol_url <>", value, "protocolUrl");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlGreaterThan(String value) {
            addCriterion("protocol_url >", value, "protocolUrl");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlGreaterThanOrEqualTo(String value) {
            addCriterion("protocol_url >=", value, "protocolUrl");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlLessThan(String value) {
            addCriterion("protocol_url <", value, "protocolUrl");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlLessThanOrEqualTo(String value) {
            addCriterion("protocol_url <=", value, "protocolUrl");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlLike(String value) {
            addCriterion("protocol_url like", value, "protocolUrl");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlNotLike(String value) {
            addCriterion("protocol_url not like", value, "protocolUrl");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlIn(List<String> values) {
            addCriterion("protocol_url in", values, "protocolUrl");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlNotIn(List<String> values) {
            addCriterion("protocol_url not in", values, "protocolUrl");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlBetween(String value1, String value2) {
            addCriterion("protocol_url between", value1, value2, "protocolUrl");
            return (Criteria) this;
        }

        public Criteria andProtocolUrlNotBetween(String value1, String value2) {
            addCriterion("protocol_url not between", value1, value2, "protocolUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlIsNull() {
            addCriterion("img_url is null");
            return (Criteria) this;
        }

        public Criteria andImgUrlIsNotNull() {
            addCriterion("img_url is not null");
            return (Criteria) this;
        }

        public Criteria andImgUrlEqualTo(String value) {
            addCriterion("img_url =", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotEqualTo(String value) {
            addCriterion("img_url <>", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlGreaterThan(String value) {
            addCriterion("img_url >", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("img_url >=", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLessThan(String value) {
            addCriterion("img_url <", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLessThanOrEqualTo(String value) {
            addCriterion("img_url <=", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLike(String value) {
            addCriterion("img_url like", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotLike(String value) {
            addCriterion("img_url not like", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlIn(List<String> values) {
            addCriterion("img_url in", values, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotIn(List<String> values) {
            addCriterion("img_url not in", values, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlBetween(String value1, String value2) {
            addCriterion("img_url between", value1, value2, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotBetween(String value1, String value2) {
            addCriterion("img_url not between", value1, value2, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNull() {
            addCriterion("is_show is null");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNotNull() {
            addCriterion("is_show is not null");
            return (Criteria) this;
        }

        public Criteria andIsShowEqualTo(Byte value) {
            addCriterion("is_show =", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotEqualTo(Byte value) {
            addCriterion("is_show <>", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThan(Byte value) {
            addCriterion("is_show >", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_show >=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThan(Byte value) {
            addCriterion("is_show <", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThanOrEqualTo(Byte value) {
            addCriterion("is_show <=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowIn(List<Byte> values) {
            addCriterion("is_show in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotIn(List<Byte> values) {
            addCriterion("is_show not in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowBetween(Byte value1, Byte value2) {
            addCriterion("is_show between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotBetween(Byte value1, Byte value2) {
            addCriterion("is_show not between", value1, value2, "isShow");
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