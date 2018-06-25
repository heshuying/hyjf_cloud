package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HjhAllocationEngineExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public HjhAllocationEngineExample() {
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

        public Criteria andPlanNidIsNull() {
            addCriterion("plan_nid is null");
            return (Criteria) this;
        }

        public Criteria andPlanNidIsNotNull() {
            addCriterion("plan_nid is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNidEqualTo(String value) {
            addCriterion("plan_nid =", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotEqualTo(String value) {
            addCriterion("plan_nid <>", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidGreaterThan(String value) {
            addCriterion("plan_nid >", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidGreaterThanOrEqualTo(String value) {
            addCriterion("plan_nid >=", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidLessThan(String value) {
            addCriterion("plan_nid <", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidLessThanOrEqualTo(String value) {
            addCriterion("plan_nid <=", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidLike(String value) {
            addCriterion("plan_nid like", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotLike(String value) {
            addCriterion("plan_nid not like", value, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidIn(List<String> values) {
            addCriterion("plan_nid in", values, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotIn(List<String> values) {
            addCriterion("plan_nid not in", values, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidBetween(String value1, String value2) {
            addCriterion("plan_nid between", value1, value2, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNidNotBetween(String value1, String value2) {
            addCriterion("plan_nid not between", value1, value2, "planNid");
            return (Criteria) this;
        }

        public Criteria andPlanNameIsNull() {
            addCriterion("plan_name is null");
            return (Criteria) this;
        }

        public Criteria andPlanNameIsNotNull() {
            addCriterion("plan_name is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNameEqualTo(String value) {
            addCriterion("plan_name =", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotEqualTo(String value) {
            addCriterion("plan_name <>", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameGreaterThan(String value) {
            addCriterion("plan_name >", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameGreaterThanOrEqualTo(String value) {
            addCriterion("plan_name >=", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameLessThan(String value) {
            addCriterion("plan_name <", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameLessThanOrEqualTo(String value) {
            addCriterion("plan_name <=", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameLike(String value) {
            addCriterion("plan_name like", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotLike(String value) {
            addCriterion("plan_name not like", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameIn(List<String> values) {
            addCriterion("plan_name in", values, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotIn(List<String> values) {
            addCriterion("plan_name not in", values, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameBetween(String value1, String value2) {
            addCriterion("plan_name between", value1, value2, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotBetween(String value1, String value2) {
            addCriterion("plan_name not between", value1, value2, "planName");
            return (Criteria) this;
        }

        public Criteria andConfigAddTimeIsNull() {
            addCriterion("config_add_time is null");
            return (Criteria) this;
        }

        public Criteria andConfigAddTimeIsNotNull() {
            addCriterion("config_add_time is not null");
            return (Criteria) this;
        }

        public Criteria andConfigAddTimeEqualTo(Integer value) {
            addCriterion("config_add_time =", value, "configAddTime");
            return (Criteria) this;
        }

        public Criteria andConfigAddTimeNotEqualTo(Integer value) {
            addCriterion("config_add_time <>", value, "configAddTime");
            return (Criteria) this;
        }

        public Criteria andConfigAddTimeGreaterThan(Integer value) {
            addCriterion("config_add_time >", value, "configAddTime");
            return (Criteria) this;
        }

        public Criteria andConfigAddTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("config_add_time >=", value, "configAddTime");
            return (Criteria) this;
        }

        public Criteria andConfigAddTimeLessThan(Integer value) {
            addCriterion("config_add_time <", value, "configAddTime");
            return (Criteria) this;
        }

        public Criteria andConfigAddTimeLessThanOrEqualTo(Integer value) {
            addCriterion("config_add_time <=", value, "configAddTime");
            return (Criteria) this;
        }

        public Criteria andConfigAddTimeIn(List<Integer> values) {
            addCriterion("config_add_time in", values, "configAddTime");
            return (Criteria) this;
        }

        public Criteria andConfigAddTimeNotIn(List<Integer> values) {
            addCriterion("config_add_time not in", values, "configAddTime");
            return (Criteria) this;
        }

        public Criteria andConfigAddTimeBetween(Integer value1, Integer value2) {
            addCriterion("config_add_time between", value1, value2, "configAddTime");
            return (Criteria) this;
        }

        public Criteria andConfigAddTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("config_add_time not between", value1, value2, "configAddTime");
            return (Criteria) this;
        }

        public Criteria andConfigStatusIsNull() {
            addCriterion("config_status is null");
            return (Criteria) this;
        }

        public Criteria andConfigStatusIsNotNull() {
            addCriterion("config_status is not null");
            return (Criteria) this;
        }

        public Criteria andConfigStatusEqualTo(Integer value) {
            addCriterion("config_status =", value, "configStatus");
            return (Criteria) this;
        }

        public Criteria andConfigStatusNotEqualTo(Integer value) {
            addCriterion("config_status <>", value, "configStatus");
            return (Criteria) this;
        }

        public Criteria andConfigStatusGreaterThan(Integer value) {
            addCriterion("config_status >", value, "configStatus");
            return (Criteria) this;
        }

        public Criteria andConfigStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("config_status >=", value, "configStatus");
            return (Criteria) this;
        }

        public Criteria andConfigStatusLessThan(Integer value) {
            addCriterion("config_status <", value, "configStatus");
            return (Criteria) this;
        }

        public Criteria andConfigStatusLessThanOrEqualTo(Integer value) {
            addCriterion("config_status <=", value, "configStatus");
            return (Criteria) this;
        }

        public Criteria andConfigStatusIn(List<Integer> values) {
            addCriterion("config_status in", values, "configStatus");
            return (Criteria) this;
        }

        public Criteria andConfigStatusNotIn(List<Integer> values) {
            addCriterion("config_status not in", values, "configStatus");
            return (Criteria) this;
        }

        public Criteria andConfigStatusBetween(Integer value1, Integer value2) {
            addCriterion("config_status between", value1, value2, "configStatus");
            return (Criteria) this;
        }

        public Criteria andConfigStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("config_status not between", value1, value2, "configStatus");
            return (Criteria) this;
        }

        public Criteria andLabelIdIsNull() {
            addCriterion("label_id is null");
            return (Criteria) this;
        }

        public Criteria andLabelIdIsNotNull() {
            addCriterion("label_id is not null");
            return (Criteria) this;
        }

        public Criteria andLabelIdEqualTo(Integer value) {
            addCriterion("label_id =", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotEqualTo(Integer value) {
            addCriterion("label_id <>", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdGreaterThan(Integer value) {
            addCriterion("label_id >", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("label_id >=", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdLessThan(Integer value) {
            addCriterion("label_id <", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdLessThanOrEqualTo(Integer value) {
            addCriterion("label_id <=", value, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdIn(List<Integer> values) {
            addCriterion("label_id in", values, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotIn(List<Integer> values) {
            addCriterion("label_id not in", values, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdBetween(Integer value1, Integer value2) {
            addCriterion("label_id between", value1, value2, "labelId");
            return (Criteria) this;
        }

        public Criteria andLabelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("label_id not between", value1, value2, "labelId");
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

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Integer value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Integer value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Integer value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Integer value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Integer value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Integer> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Integer> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Integer value1, Integer value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andLabelSortIsNull() {
            addCriterion("label_sort is null");
            return (Criteria) this;
        }

        public Criteria andLabelSortIsNotNull() {
            addCriterion("label_sort is not null");
            return (Criteria) this;
        }

        public Criteria andLabelSortEqualTo(Integer value) {
            addCriterion("label_sort =", value, "labelSort");
            return (Criteria) this;
        }

        public Criteria andLabelSortNotEqualTo(Integer value) {
            addCriterion("label_sort <>", value, "labelSort");
            return (Criteria) this;
        }

        public Criteria andLabelSortGreaterThan(Integer value) {
            addCriterion("label_sort >", value, "labelSort");
            return (Criteria) this;
        }

        public Criteria andLabelSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("label_sort >=", value, "labelSort");
            return (Criteria) this;
        }

        public Criteria andLabelSortLessThan(Integer value) {
            addCriterion("label_sort <", value, "labelSort");
            return (Criteria) this;
        }

        public Criteria andLabelSortLessThanOrEqualTo(Integer value) {
            addCriterion("label_sort <=", value, "labelSort");
            return (Criteria) this;
        }

        public Criteria andLabelSortIn(List<Integer> values) {
            addCriterion("label_sort in", values, "labelSort");
            return (Criteria) this;
        }

        public Criteria andLabelSortNotIn(List<Integer> values) {
            addCriterion("label_sort not in", values, "labelSort");
            return (Criteria) this;
        }

        public Criteria andLabelSortBetween(Integer value1, Integer value2) {
            addCriterion("label_sort between", value1, value2, "labelSort");
            return (Criteria) this;
        }

        public Criteria andLabelSortNotBetween(Integer value1, Integer value2) {
            addCriterion("label_sort not between", value1, value2, "labelSort");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortIsNull() {
            addCriterion("transfer_time_sort is null");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortIsNotNull() {
            addCriterion("transfer_time_sort is not null");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortEqualTo(Integer value) {
            addCriterion("transfer_time_sort =", value, "transferTimeSort");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortNotEqualTo(Integer value) {
            addCriterion("transfer_time_sort <>", value, "transferTimeSort");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortGreaterThan(Integer value) {
            addCriterion("transfer_time_sort >", value, "transferTimeSort");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("transfer_time_sort >=", value, "transferTimeSort");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortLessThan(Integer value) {
            addCriterion("transfer_time_sort <", value, "transferTimeSort");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortLessThanOrEqualTo(Integer value) {
            addCriterion("transfer_time_sort <=", value, "transferTimeSort");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortIn(List<Integer> values) {
            addCriterion("transfer_time_sort in", values, "transferTimeSort");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortNotIn(List<Integer> values) {
            addCriterion("transfer_time_sort not in", values, "transferTimeSort");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortBetween(Integer value1, Integer value2) {
            addCriterion("transfer_time_sort between", value1, value2, "transferTimeSort");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortNotBetween(Integer value1, Integer value2) {
            addCriterion("transfer_time_sort not between", value1, value2, "transferTimeSort");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortPriorityIsNull() {
            addCriterion("transfer_time_sort_priority is null");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortPriorityIsNotNull() {
            addCriterion("transfer_time_sort_priority is not null");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortPriorityEqualTo(Integer value) {
            addCriterion("transfer_time_sort_priority =", value, "transferTimeSortPriority");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortPriorityNotEqualTo(Integer value) {
            addCriterion("transfer_time_sort_priority <>", value, "transferTimeSortPriority");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortPriorityGreaterThan(Integer value) {
            addCriterion("transfer_time_sort_priority >", value, "transferTimeSortPriority");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("transfer_time_sort_priority >=", value, "transferTimeSortPriority");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortPriorityLessThan(Integer value) {
            addCriterion("transfer_time_sort_priority <", value, "transferTimeSortPriority");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("transfer_time_sort_priority <=", value, "transferTimeSortPriority");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortPriorityIn(List<Integer> values) {
            addCriterion("transfer_time_sort_priority in", values, "transferTimeSortPriority");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortPriorityNotIn(List<Integer> values) {
            addCriterion("transfer_time_sort_priority not in", values, "transferTimeSortPriority");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortPriorityBetween(Integer value1, Integer value2) {
            addCriterion("transfer_time_sort_priority between", value1, value2, "transferTimeSortPriority");
            return (Criteria) this;
        }

        public Criteria andTransferTimeSortPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("transfer_time_sort_priority not between", value1, value2, "transferTimeSortPriority");
            return (Criteria) this;
        }

        public Criteria andAprSortIsNull() {
            addCriterion("apr_sort is null");
            return (Criteria) this;
        }

        public Criteria andAprSortIsNotNull() {
            addCriterion("apr_sort is not null");
            return (Criteria) this;
        }

        public Criteria andAprSortEqualTo(Integer value) {
            addCriterion("apr_sort =", value, "aprSort");
            return (Criteria) this;
        }

        public Criteria andAprSortNotEqualTo(Integer value) {
            addCriterion("apr_sort <>", value, "aprSort");
            return (Criteria) this;
        }

        public Criteria andAprSortGreaterThan(Integer value) {
            addCriterion("apr_sort >", value, "aprSort");
            return (Criteria) this;
        }

        public Criteria andAprSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("apr_sort >=", value, "aprSort");
            return (Criteria) this;
        }

        public Criteria andAprSortLessThan(Integer value) {
            addCriterion("apr_sort <", value, "aprSort");
            return (Criteria) this;
        }

        public Criteria andAprSortLessThanOrEqualTo(Integer value) {
            addCriterion("apr_sort <=", value, "aprSort");
            return (Criteria) this;
        }

        public Criteria andAprSortIn(List<Integer> values) {
            addCriterion("apr_sort in", values, "aprSort");
            return (Criteria) this;
        }

        public Criteria andAprSortNotIn(List<Integer> values) {
            addCriterion("apr_sort not in", values, "aprSort");
            return (Criteria) this;
        }

        public Criteria andAprSortBetween(Integer value1, Integer value2) {
            addCriterion("apr_sort between", value1, value2, "aprSort");
            return (Criteria) this;
        }

        public Criteria andAprSortNotBetween(Integer value1, Integer value2) {
            addCriterion("apr_sort not between", value1, value2, "aprSort");
            return (Criteria) this;
        }

        public Criteria andAprSortPriorityIsNull() {
            addCriterion("apr_sort_priority is null");
            return (Criteria) this;
        }

        public Criteria andAprSortPriorityIsNotNull() {
            addCriterion("apr_sort_priority is not null");
            return (Criteria) this;
        }

        public Criteria andAprSortPriorityEqualTo(Integer value) {
            addCriterion("apr_sort_priority =", value, "aprSortPriority");
            return (Criteria) this;
        }

        public Criteria andAprSortPriorityNotEqualTo(Integer value) {
            addCriterion("apr_sort_priority <>", value, "aprSortPriority");
            return (Criteria) this;
        }

        public Criteria andAprSortPriorityGreaterThan(Integer value) {
            addCriterion("apr_sort_priority >", value, "aprSortPriority");
            return (Criteria) this;
        }

        public Criteria andAprSortPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("apr_sort_priority >=", value, "aprSortPriority");
            return (Criteria) this;
        }

        public Criteria andAprSortPriorityLessThan(Integer value) {
            addCriterion("apr_sort_priority <", value, "aprSortPriority");
            return (Criteria) this;
        }

        public Criteria andAprSortPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("apr_sort_priority <=", value, "aprSortPriority");
            return (Criteria) this;
        }

        public Criteria andAprSortPriorityIn(List<Integer> values) {
            addCriterion("apr_sort_priority in", values, "aprSortPriority");
            return (Criteria) this;
        }

        public Criteria andAprSortPriorityNotIn(List<Integer> values) {
            addCriterion("apr_sort_priority not in", values, "aprSortPriority");
            return (Criteria) this;
        }

        public Criteria andAprSortPriorityBetween(Integer value1, Integer value2) {
            addCriterion("apr_sort_priority between", value1, value2, "aprSortPriority");
            return (Criteria) this;
        }

        public Criteria andAprSortPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("apr_sort_priority not between", value1, value2, "aprSortPriority");
            return (Criteria) this;
        }

        public Criteria andActulPaySortIsNull() {
            addCriterion("actul_pay_sort is null");
            return (Criteria) this;
        }

        public Criteria andActulPaySortIsNotNull() {
            addCriterion("actul_pay_sort is not null");
            return (Criteria) this;
        }

        public Criteria andActulPaySortEqualTo(Integer value) {
            addCriterion("actul_pay_sort =", value, "actulPaySort");
            return (Criteria) this;
        }

        public Criteria andActulPaySortNotEqualTo(Integer value) {
            addCriterion("actul_pay_sort <>", value, "actulPaySort");
            return (Criteria) this;
        }

        public Criteria andActulPaySortGreaterThan(Integer value) {
            addCriterion("actul_pay_sort >", value, "actulPaySort");
            return (Criteria) this;
        }

        public Criteria andActulPaySortGreaterThanOrEqualTo(Integer value) {
            addCriterion("actul_pay_sort >=", value, "actulPaySort");
            return (Criteria) this;
        }

        public Criteria andActulPaySortLessThan(Integer value) {
            addCriterion("actul_pay_sort <", value, "actulPaySort");
            return (Criteria) this;
        }

        public Criteria andActulPaySortLessThanOrEqualTo(Integer value) {
            addCriterion("actul_pay_sort <=", value, "actulPaySort");
            return (Criteria) this;
        }

        public Criteria andActulPaySortIn(List<Integer> values) {
            addCriterion("actul_pay_sort in", values, "actulPaySort");
            return (Criteria) this;
        }

        public Criteria andActulPaySortNotIn(List<Integer> values) {
            addCriterion("actul_pay_sort not in", values, "actulPaySort");
            return (Criteria) this;
        }

        public Criteria andActulPaySortBetween(Integer value1, Integer value2) {
            addCriterion("actul_pay_sort between", value1, value2, "actulPaySort");
            return (Criteria) this;
        }

        public Criteria andActulPaySortNotBetween(Integer value1, Integer value2) {
            addCriterion("actul_pay_sort not between", value1, value2, "actulPaySort");
            return (Criteria) this;
        }

        public Criteria andActulPaySortPriorityIsNull() {
            addCriterion("actul_pay_sort_priority is null");
            return (Criteria) this;
        }

        public Criteria andActulPaySortPriorityIsNotNull() {
            addCriterion("actul_pay_sort_priority is not null");
            return (Criteria) this;
        }

        public Criteria andActulPaySortPriorityEqualTo(Integer value) {
            addCriterion("actul_pay_sort_priority =", value, "actulPaySortPriority");
            return (Criteria) this;
        }

        public Criteria andActulPaySortPriorityNotEqualTo(Integer value) {
            addCriterion("actul_pay_sort_priority <>", value, "actulPaySortPriority");
            return (Criteria) this;
        }

        public Criteria andActulPaySortPriorityGreaterThan(Integer value) {
            addCriterion("actul_pay_sort_priority >", value, "actulPaySortPriority");
            return (Criteria) this;
        }

        public Criteria andActulPaySortPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("actul_pay_sort_priority >=", value, "actulPaySortPriority");
            return (Criteria) this;
        }

        public Criteria andActulPaySortPriorityLessThan(Integer value) {
            addCriterion("actul_pay_sort_priority <", value, "actulPaySortPriority");
            return (Criteria) this;
        }

        public Criteria andActulPaySortPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("actul_pay_sort_priority <=", value, "actulPaySortPriority");
            return (Criteria) this;
        }

        public Criteria andActulPaySortPriorityIn(List<Integer> values) {
            addCriterion("actul_pay_sort_priority in", values, "actulPaySortPriority");
            return (Criteria) this;
        }

        public Criteria andActulPaySortPriorityNotIn(List<Integer> values) {
            addCriterion("actul_pay_sort_priority not in", values, "actulPaySortPriority");
            return (Criteria) this;
        }

        public Criteria andActulPaySortPriorityBetween(Integer value1, Integer value2) {
            addCriterion("actul_pay_sort_priority between", value1, value2, "actulPaySortPriority");
            return (Criteria) this;
        }

        public Criteria andActulPaySortPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("actul_pay_sort_priority not between", value1, value2, "actulPaySortPriority");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortIsNull() {
            addCriterion("invest_progress_sort is null");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortIsNotNull() {
            addCriterion("invest_progress_sort is not null");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortEqualTo(Integer value) {
            addCriterion("invest_progress_sort =", value, "investProgressSort");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortNotEqualTo(Integer value) {
            addCriterion("invest_progress_sort <>", value, "investProgressSort");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortGreaterThan(Integer value) {
            addCriterion("invest_progress_sort >", value, "investProgressSort");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_progress_sort >=", value, "investProgressSort");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortLessThan(Integer value) {
            addCriterion("invest_progress_sort <", value, "investProgressSort");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortLessThanOrEqualTo(Integer value) {
            addCriterion("invest_progress_sort <=", value, "investProgressSort");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortIn(List<Integer> values) {
            addCriterion("invest_progress_sort in", values, "investProgressSort");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortNotIn(List<Integer> values) {
            addCriterion("invest_progress_sort not in", values, "investProgressSort");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortBetween(Integer value1, Integer value2) {
            addCriterion("invest_progress_sort between", value1, value2, "investProgressSort");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_progress_sort not between", value1, value2, "investProgressSort");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortPriorityIsNull() {
            addCriterion("invest_progress_sort_priority is null");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortPriorityIsNotNull() {
            addCriterion("invest_progress_sort_priority is not null");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortPriorityEqualTo(Integer value) {
            addCriterion("invest_progress_sort_priority =", value, "investProgressSortPriority");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortPriorityNotEqualTo(Integer value) {
            addCriterion("invest_progress_sort_priority <>", value, "investProgressSortPriority");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortPriorityGreaterThan(Integer value) {
            addCriterion("invest_progress_sort_priority >", value, "investProgressSortPriority");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_progress_sort_priority >=", value, "investProgressSortPriority");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortPriorityLessThan(Integer value) {
            addCriterion("invest_progress_sort_priority <", value, "investProgressSortPriority");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("invest_progress_sort_priority <=", value, "investProgressSortPriority");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortPriorityIn(List<Integer> values) {
            addCriterion("invest_progress_sort_priority in", values, "investProgressSortPriority");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortPriorityNotIn(List<Integer> values) {
            addCriterion("invest_progress_sort_priority not in", values, "investProgressSortPriority");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortPriorityBetween(Integer value1, Integer value2) {
            addCriterion("invest_progress_sort_priority between", value1, value2, "investProgressSortPriority");
            return (Criteria) this;
        }

        public Criteria andInvestProgressSortPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_progress_sort_priority not between", value1, value2, "investProgressSortPriority");
            return (Criteria) this;
        }

        public Criteria andLabelStatusIsNull() {
            addCriterion("label_status is null");
            return (Criteria) this;
        }

        public Criteria andLabelStatusIsNotNull() {
            addCriterion("label_status is not null");
            return (Criteria) this;
        }

        public Criteria andLabelStatusEqualTo(Integer value) {
            addCriterion("label_status =", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusNotEqualTo(Integer value) {
            addCriterion("label_status <>", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusGreaterThan(Integer value) {
            addCriterion("label_status >", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("label_status >=", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusLessThan(Integer value) {
            addCriterion("label_status <", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusLessThanOrEqualTo(Integer value) {
            addCriterion("label_status <=", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusIn(List<Integer> values) {
            addCriterion("label_status in", values, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusNotIn(List<Integer> values) {
            addCriterion("label_status not in", values, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusBetween(Integer value1, Integer value2) {
            addCriterion("label_status between", value1, value2, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("label_status not between", value1, value2, "labelStatus");
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

        public Criteria andCreateUserEqualTo(Integer value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Integer value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Integer value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Integer value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Integer value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Integer> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Integer> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Integer value1, Integer value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
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

        public Criteria andUpdateUserEqualTo(Integer value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(Integer value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(Integer value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(Integer value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(Integer value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<Integer> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<Integer> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(Integer value1, Integer value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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