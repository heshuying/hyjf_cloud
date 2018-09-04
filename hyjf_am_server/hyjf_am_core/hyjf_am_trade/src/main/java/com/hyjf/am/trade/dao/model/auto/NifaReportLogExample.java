package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NifaReportLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public NifaReportLogExample() {
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

        public Criteria andPackageInformationIsNull() {
            addCriterion("package_information is null");
            return (Criteria) this;
        }

        public Criteria andPackageInformationIsNotNull() {
            addCriterion("package_information is not null");
            return (Criteria) this;
        }

        public Criteria andPackageInformationEqualTo(String value) {
            addCriterion("package_information =", value, "packageInformation");
            return (Criteria) this;
        }

        public Criteria andPackageInformationNotEqualTo(String value) {
            addCriterion("package_information <>", value, "packageInformation");
            return (Criteria) this;
        }

        public Criteria andPackageInformationGreaterThan(String value) {
            addCriterion("package_information >", value, "packageInformation");
            return (Criteria) this;
        }

        public Criteria andPackageInformationGreaterThanOrEqualTo(String value) {
            addCriterion("package_information >=", value, "packageInformation");
            return (Criteria) this;
        }

        public Criteria andPackageInformationLessThan(String value) {
            addCriterion("package_information <", value, "packageInformation");
            return (Criteria) this;
        }

        public Criteria andPackageInformationLessThanOrEqualTo(String value) {
            addCriterion("package_information <=", value, "packageInformation");
            return (Criteria) this;
        }

        public Criteria andPackageInformationLike(String value) {
            addCriterion("package_information like", value, "packageInformation");
            return (Criteria) this;
        }

        public Criteria andPackageInformationNotLike(String value) {
            addCriterion("package_information not like", value, "packageInformation");
            return (Criteria) this;
        }

        public Criteria andPackageInformationIn(List<String> values) {
            addCriterion("package_information in", values, "packageInformation");
            return (Criteria) this;
        }

        public Criteria andPackageInformationNotIn(List<String> values) {
            addCriterion("package_information not in", values, "packageInformation");
            return (Criteria) this;
        }

        public Criteria andPackageInformationBetween(String value1, String value2) {
            addCriterion("package_information between", value1, value2, "packageInformation");
            return (Criteria) this;
        }

        public Criteria andPackageInformationNotBetween(String value1, String value2) {
            addCriterion("package_information not between", value1, value2, "packageInformation");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNull() {
            addCriterion("upload_time is null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNotNull() {
            addCriterion("upload_time is not null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeEqualTo(Integer value) {
            addCriterion("upload_time =", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotEqualTo(Integer value) {
            addCriterion("upload_time <>", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThan(Integer value) {
            addCriterion("upload_time >", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("upload_time >=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThan(Integer value) {
            addCriterion("upload_time <", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThanOrEqualTo(Integer value) {
            addCriterion("upload_time <=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIn(List<Integer> values) {
            addCriterion("upload_time in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotIn(List<Integer> values) {
            addCriterion("upload_time not in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeBetween(Integer value1, Integer value2) {
            addCriterion("upload_time between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("upload_time not between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andFileUploadStatusIsNull() {
            addCriterion("file_upload_status is null");
            return (Criteria) this;
        }

        public Criteria andFileUploadStatusIsNotNull() {
            addCriterion("file_upload_status is not null");
            return (Criteria) this;
        }

        public Criteria andFileUploadStatusEqualTo(Integer value) {
            addCriterion("file_upload_status =", value, "fileUploadStatus");
            return (Criteria) this;
        }

        public Criteria andFileUploadStatusNotEqualTo(Integer value) {
            addCriterion("file_upload_status <>", value, "fileUploadStatus");
            return (Criteria) this;
        }

        public Criteria andFileUploadStatusGreaterThan(Integer value) {
            addCriterion("file_upload_status >", value, "fileUploadStatus");
            return (Criteria) this;
        }

        public Criteria andFileUploadStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("file_upload_status >=", value, "fileUploadStatus");
            return (Criteria) this;
        }

        public Criteria andFileUploadStatusLessThan(Integer value) {
            addCriterion("file_upload_status <", value, "fileUploadStatus");
            return (Criteria) this;
        }

        public Criteria andFileUploadStatusLessThanOrEqualTo(Integer value) {
            addCriterion("file_upload_status <=", value, "fileUploadStatus");
            return (Criteria) this;
        }

        public Criteria andFileUploadStatusIn(List<Integer> values) {
            addCriterion("file_upload_status in", values, "fileUploadStatus");
            return (Criteria) this;
        }

        public Criteria andFileUploadStatusNotIn(List<Integer> values) {
            addCriterion("file_upload_status not in", values, "fileUploadStatus");
            return (Criteria) this;
        }

        public Criteria andFileUploadStatusBetween(Integer value1, Integer value2) {
            addCriterion("file_upload_status between", value1, value2, "fileUploadStatus");
            return (Criteria) this;
        }

        public Criteria andFileUploadStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("file_upload_status not between", value1, value2, "fileUploadStatus");
            return (Criteria) this;
        }

        public Criteria andFeedbackResultIsNull() {
            addCriterion("feedback_result is null");
            return (Criteria) this;
        }

        public Criteria andFeedbackResultIsNotNull() {
            addCriterion("feedback_result is not null");
            return (Criteria) this;
        }

        public Criteria andFeedbackResultEqualTo(Integer value) {
            addCriterion("feedback_result =", value, "feedbackResult");
            return (Criteria) this;
        }

        public Criteria andFeedbackResultNotEqualTo(Integer value) {
            addCriterion("feedback_result <>", value, "feedbackResult");
            return (Criteria) this;
        }

        public Criteria andFeedbackResultGreaterThan(Integer value) {
            addCriterion("feedback_result >", value, "feedbackResult");
            return (Criteria) this;
        }

        public Criteria andFeedbackResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("feedback_result >=", value, "feedbackResult");
            return (Criteria) this;
        }

        public Criteria andFeedbackResultLessThan(Integer value) {
            addCriterion("feedback_result <", value, "feedbackResult");
            return (Criteria) this;
        }

        public Criteria andFeedbackResultLessThanOrEqualTo(Integer value) {
            addCriterion("feedback_result <=", value, "feedbackResult");
            return (Criteria) this;
        }

        public Criteria andFeedbackResultIn(List<Integer> values) {
            addCriterion("feedback_result in", values, "feedbackResult");
            return (Criteria) this;
        }

        public Criteria andFeedbackResultNotIn(List<Integer> values) {
            addCriterion("feedback_result not in", values, "feedbackResult");
            return (Criteria) this;
        }

        public Criteria andFeedbackResultBetween(Integer value1, Integer value2) {
            addCriterion("feedback_result between", value1, value2, "feedbackResult");
            return (Criteria) this;
        }

        public Criteria andFeedbackResultNotBetween(Integer value1, Integer value2) {
            addCriterion("feedback_result not between", value1, value2, "feedbackResult");
            return (Criteria) this;
        }

        public Criteria andUploadNameIsNull() {
            addCriterion("upload_name is null");
            return (Criteria) this;
        }

        public Criteria andUploadNameIsNotNull() {
            addCriterion("upload_name is not null");
            return (Criteria) this;
        }

        public Criteria andUploadNameEqualTo(String value) {
            addCriterion("upload_name =", value, "uploadName");
            return (Criteria) this;
        }

        public Criteria andUploadNameNotEqualTo(String value) {
            addCriterion("upload_name <>", value, "uploadName");
            return (Criteria) this;
        }

        public Criteria andUploadNameGreaterThan(String value) {
            addCriterion("upload_name >", value, "uploadName");
            return (Criteria) this;
        }

        public Criteria andUploadNameGreaterThanOrEqualTo(String value) {
            addCriterion("upload_name >=", value, "uploadName");
            return (Criteria) this;
        }

        public Criteria andUploadNameLessThan(String value) {
            addCriterion("upload_name <", value, "uploadName");
            return (Criteria) this;
        }

        public Criteria andUploadNameLessThanOrEqualTo(String value) {
            addCriterion("upload_name <=", value, "uploadName");
            return (Criteria) this;
        }

        public Criteria andUploadNameLike(String value) {
            addCriterion("upload_name like", value, "uploadName");
            return (Criteria) this;
        }

        public Criteria andUploadNameNotLike(String value) {
            addCriterion("upload_name not like", value, "uploadName");
            return (Criteria) this;
        }

        public Criteria andUploadNameIn(List<String> values) {
            addCriterion("upload_name in", values, "uploadName");
            return (Criteria) this;
        }

        public Criteria andUploadNameNotIn(List<String> values) {
            addCriterion("upload_name not in", values, "uploadName");
            return (Criteria) this;
        }

        public Criteria andUploadNameBetween(String value1, String value2) {
            addCriterion("upload_name between", value1, value2, "uploadName");
            return (Criteria) this;
        }

        public Criteria andUploadNameNotBetween(String value1, String value2) {
            addCriterion("upload_name not between", value1, value2, "uploadName");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameIsNull() {
            addCriterion("feedback_name is null");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameIsNotNull() {
            addCriterion("feedback_name is not null");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameEqualTo(String value) {
            addCriterion("feedback_name =", value, "feedbackName");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameNotEqualTo(String value) {
            addCriterion("feedback_name <>", value, "feedbackName");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameGreaterThan(String value) {
            addCriterion("feedback_name >", value, "feedbackName");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameGreaterThanOrEqualTo(String value) {
            addCriterion("feedback_name >=", value, "feedbackName");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameLessThan(String value) {
            addCriterion("feedback_name <", value, "feedbackName");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameLessThanOrEqualTo(String value) {
            addCriterion("feedback_name <=", value, "feedbackName");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameLike(String value) {
            addCriterion("feedback_name like", value, "feedbackName");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameNotLike(String value) {
            addCriterion("feedback_name not like", value, "feedbackName");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameIn(List<String> values) {
            addCriterion("feedback_name in", values, "feedbackName");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameNotIn(List<String> values) {
            addCriterion("feedback_name not in", values, "feedbackName");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameBetween(String value1, String value2) {
            addCriterion("feedback_name between", value1, value2, "feedbackName");
            return (Criteria) this;
        }

        public Criteria andFeedbackNameNotBetween(String value1, String value2) {
            addCriterion("feedback_name not between", value1, value2, "feedbackName");
            return (Criteria) this;
        }

        public Criteria andUploadPathIsNull() {
            addCriterion("upload_path is null");
            return (Criteria) this;
        }

        public Criteria andUploadPathIsNotNull() {
            addCriterion("upload_path is not null");
            return (Criteria) this;
        }

        public Criteria andUploadPathEqualTo(String value) {
            addCriterion("upload_path =", value, "uploadPath");
            return (Criteria) this;
        }

        public Criteria andUploadPathNotEqualTo(String value) {
            addCriterion("upload_path <>", value, "uploadPath");
            return (Criteria) this;
        }

        public Criteria andUploadPathGreaterThan(String value) {
            addCriterion("upload_path >", value, "uploadPath");
            return (Criteria) this;
        }

        public Criteria andUploadPathGreaterThanOrEqualTo(String value) {
            addCriterion("upload_path >=", value, "uploadPath");
            return (Criteria) this;
        }

        public Criteria andUploadPathLessThan(String value) {
            addCriterion("upload_path <", value, "uploadPath");
            return (Criteria) this;
        }

        public Criteria andUploadPathLessThanOrEqualTo(String value) {
            addCriterion("upload_path <=", value, "uploadPath");
            return (Criteria) this;
        }

        public Criteria andUploadPathLike(String value) {
            addCriterion("upload_path like", value, "uploadPath");
            return (Criteria) this;
        }

        public Criteria andUploadPathNotLike(String value) {
            addCriterion("upload_path not like", value, "uploadPath");
            return (Criteria) this;
        }

        public Criteria andUploadPathIn(List<String> values) {
            addCriterion("upload_path in", values, "uploadPath");
            return (Criteria) this;
        }

        public Criteria andUploadPathNotIn(List<String> values) {
            addCriterion("upload_path not in", values, "uploadPath");
            return (Criteria) this;
        }

        public Criteria andUploadPathBetween(String value1, String value2) {
            addCriterion("upload_path between", value1, value2, "uploadPath");
            return (Criteria) this;
        }

        public Criteria andUploadPathNotBetween(String value1, String value2) {
            addCriterion("upload_path not between", value1, value2, "uploadPath");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathIsNull() {
            addCriterion("feedback_path is null");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathIsNotNull() {
            addCriterion("feedback_path is not null");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathEqualTo(String value) {
            addCriterion("feedback_path =", value, "feedbackPath");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathNotEqualTo(String value) {
            addCriterion("feedback_path <>", value, "feedbackPath");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathGreaterThan(String value) {
            addCriterion("feedback_path >", value, "feedbackPath");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathGreaterThanOrEqualTo(String value) {
            addCriterion("feedback_path >=", value, "feedbackPath");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathLessThan(String value) {
            addCriterion("feedback_path <", value, "feedbackPath");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathLessThanOrEqualTo(String value) {
            addCriterion("feedback_path <=", value, "feedbackPath");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathLike(String value) {
            addCriterion("feedback_path like", value, "feedbackPath");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathNotLike(String value) {
            addCriterion("feedback_path not like", value, "feedbackPath");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathIn(List<String> values) {
            addCriterion("feedback_path in", values, "feedbackPath");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathNotIn(List<String> values) {
            addCriterion("feedback_path not in", values, "feedbackPath");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathBetween(String value1, String value2) {
            addCriterion("feedback_path between", value1, value2, "feedbackPath");
            return (Criteria) this;
        }

        public Criteria andFeedbackPathNotBetween(String value1, String value2) {
            addCriterion("feedback_path not between", value1, value2, "feedbackPath");
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