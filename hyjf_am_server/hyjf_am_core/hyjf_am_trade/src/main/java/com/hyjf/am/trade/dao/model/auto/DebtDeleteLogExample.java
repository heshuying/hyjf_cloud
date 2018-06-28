package com.hyjf.am.trade.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DebtDeleteLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public DebtDeleteLogExample() {
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

        public Criteria andBorrowNameIsNull() {
            addCriterion("borrow_name is null");
            return (Criteria) this;
        }

        public Criteria andBorrowNameIsNotNull() {
            addCriterion("borrow_name is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowNameEqualTo(String value) {
            addCriterion("borrow_name =", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameNotEqualTo(String value) {
            addCriterion("borrow_name <>", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameGreaterThan(String value) {
            addCriterion("borrow_name >", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_name >=", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameLessThan(String value) {
            addCriterion("borrow_name <", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameLessThanOrEqualTo(String value) {
            addCriterion("borrow_name <=", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameLike(String value) {
            addCriterion("borrow_name like", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameNotLike(String value) {
            addCriterion("borrow_name not like", value, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameIn(List<String> values) {
            addCriterion("borrow_name in", values, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameNotIn(List<String> values) {
            addCriterion("borrow_name not in", values, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameBetween(String value1, String value2) {
            addCriterion("borrow_name between", value1, value2, "borrowName");
            return (Criteria) this;
        }

        public Criteria andBorrowNameNotBetween(String value1, String value2) {
            addCriterion("borrow_name not between", value1, value2, "borrowName");
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

        public Criteria andAccountIsNull() {
            addCriterion("account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(String value) {
            addCriterion("account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(String value) {
            addCriterion("account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(String value) {
            addCriterion("account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(String value) {
            addCriterion("account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(String value) {
            addCriterion("account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(String value) {
            addCriterion("account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLike(String value) {
            addCriterion("account like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotLike(String value) {
            addCriterion("account not like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<String> values) {
            addCriterion("account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<String> values) {
            addCriterion("account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(String value1, String value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(String value1, String value2) {
            addCriterion("account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesIsNull() {
            addCriterion("borrow_account_yes is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesIsNotNull() {
            addCriterion("borrow_account_yes is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesEqualTo(String value) {
            addCriterion("borrow_account_yes =", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesNotEqualTo(String value) {
            addCriterion("borrow_account_yes <>", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesGreaterThan(String value) {
            addCriterion("borrow_account_yes >", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_account_yes >=", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesLessThan(String value) {
            addCriterion("borrow_account_yes <", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesLessThanOrEqualTo(String value) {
            addCriterion("borrow_account_yes <=", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesLike(String value) {
            addCriterion("borrow_account_yes like", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesNotLike(String value) {
            addCriterion("borrow_account_yes not like", value, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesIn(List<String> values) {
            addCriterion("borrow_account_yes in", values, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesNotIn(List<String> values) {
            addCriterion("borrow_account_yes not in", values, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesBetween(String value1, String value2) {
            addCriterion("borrow_account_yes between", value1, value2, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountYesNotBetween(String value1, String value2) {
            addCriterion("borrow_account_yes not between", value1, value2, "borrowAccountYes");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitIsNull() {
            addCriterion("borrow_account_wait is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitIsNotNull() {
            addCriterion("borrow_account_wait is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitEqualTo(String value) {
            addCriterion("borrow_account_wait =", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitNotEqualTo(String value) {
            addCriterion("borrow_account_wait <>", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitGreaterThan(String value) {
            addCriterion("borrow_account_wait >", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_account_wait >=", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitLessThan(String value) {
            addCriterion("borrow_account_wait <", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitLessThanOrEqualTo(String value) {
            addCriterion("borrow_account_wait <=", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitLike(String value) {
            addCriterion("borrow_account_wait like", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitNotLike(String value) {
            addCriterion("borrow_account_wait not like", value, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitIn(List<String> values) {
            addCriterion("borrow_account_wait in", values, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitNotIn(List<String> values) {
            addCriterion("borrow_account_wait not in", values, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitBetween(String value1, String value2) {
            addCriterion("borrow_account_wait between", value1, value2, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountWaitNotBetween(String value1, String value2) {
            addCriterion("borrow_account_wait not between", value1, value2, "borrowAccountWait");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleIsNull() {
            addCriterion("borrow_account_scale is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleIsNotNull() {
            addCriterion("borrow_account_scale is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleEqualTo(String value) {
            addCriterion("borrow_account_scale =", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleNotEqualTo(String value) {
            addCriterion("borrow_account_scale <>", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleGreaterThan(String value) {
            addCriterion("borrow_account_scale >", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_account_scale >=", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleLessThan(String value) {
            addCriterion("borrow_account_scale <", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleLessThanOrEqualTo(String value) {
            addCriterion("borrow_account_scale <=", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleLike(String value) {
            addCriterion("borrow_account_scale like", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleNotLike(String value) {
            addCriterion("borrow_account_scale not like", value, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleIn(List<String> values) {
            addCriterion("borrow_account_scale in", values, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleNotIn(List<String> values) {
            addCriterion("borrow_account_scale not in", values, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleBetween(String value1, String value2) {
            addCriterion("borrow_account_scale between", value1, value2, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowAccountScaleNotBetween(String value1, String value2) {
            addCriterion("borrow_account_scale not between", value1, value2, "borrowAccountScale");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIsNull() {
            addCriterion("borrow_style is null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIsNotNull() {
            addCriterion("borrow_style is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleEqualTo(String value) {
            addCriterion("borrow_style =", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotEqualTo(String value) {
            addCriterion("borrow_style <>", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleGreaterThan(String value) {
            addCriterion("borrow_style >", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_style >=", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLessThan(String value) {
            addCriterion("borrow_style <", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLessThanOrEqualTo(String value) {
            addCriterion("borrow_style <=", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleLike(String value) {
            addCriterion("borrow_style like", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotLike(String value) {
            addCriterion("borrow_style not like", value, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleIn(List<String> values) {
            addCriterion("borrow_style in", values, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotIn(List<String> values) {
            addCriterion("borrow_style not in", values, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleBetween(String value1, String value2) {
            addCriterion("borrow_style between", value1, value2, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNotBetween(String value1, String value2) {
            addCriterion("borrow_style not between", value1, value2, "borrowStyle");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameIsNull() {
            addCriterion("borrow_style_name is null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameIsNotNull() {
            addCriterion("borrow_style_name is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameEqualTo(String value) {
            addCriterion("borrow_style_name =", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameNotEqualTo(String value) {
            addCriterion("borrow_style_name <>", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameGreaterThan(String value) {
            addCriterion("borrow_style_name >", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_style_name >=", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameLessThan(String value) {
            addCriterion("borrow_style_name <", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameLessThanOrEqualTo(String value) {
            addCriterion("borrow_style_name <=", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameLike(String value) {
            addCriterion("borrow_style_name like", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameNotLike(String value) {
            addCriterion("borrow_style_name not like", value, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameIn(List<String> values) {
            addCriterion("borrow_style_name in", values, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameNotIn(List<String> values) {
            addCriterion("borrow_style_name not in", values, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameBetween(String value1, String value2) {
            addCriterion("borrow_style_name between", value1, value2, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andBorrowStyleNameNotBetween(String value1, String value2) {
            addCriterion("borrow_style_name not between", value1, value2, "borrowStyleName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNull() {
            addCriterion("project_type is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNotNull() {
            addCriterion("project_type is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeEqualTo(Integer value) {
            addCriterion("project_type =", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotEqualTo(Integer value) {
            addCriterion("project_type <>", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThan(Integer value) {
            addCriterion("project_type >", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_type >=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThan(Integer value) {
            addCriterion("project_type <", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThanOrEqualTo(Integer value) {
            addCriterion("project_type <=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIn(List<Integer> values) {
            addCriterion("project_type in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotIn(List<Integer> values) {
            addCriterion("project_type not in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeBetween(Integer value1, Integer value2) {
            addCriterion("project_type between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("project_type not between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameIsNull() {
            addCriterion("project_type_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameIsNotNull() {
            addCriterion("project_type_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameEqualTo(String value) {
            addCriterion("project_type_name =", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameNotEqualTo(String value) {
            addCriterion("project_type_name <>", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameGreaterThan(String value) {
            addCriterion("project_type_name >", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_type_name >=", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameLessThan(String value) {
            addCriterion("project_type_name <", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameLessThanOrEqualTo(String value) {
            addCriterion("project_type_name <=", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameLike(String value) {
            addCriterion("project_type_name like", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameNotLike(String value) {
            addCriterion("project_type_name not like", value, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameIn(List<String> values) {
            addCriterion("project_type_name in", values, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameNotIn(List<String> values) {
            addCriterion("project_type_name not in", values, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameBetween(String value1, String value2) {
            addCriterion("project_type_name between", value1, value2, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNameNotBetween(String value1, String value2) {
            addCriterion("project_type_name not between", value1, value2, "projectTypeName");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIsNull() {
            addCriterion("borrow_period is null");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIsNotNull() {
            addCriterion("borrow_period is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodEqualTo(String value) {
            addCriterion("borrow_period =", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotEqualTo(String value) {
            addCriterion("borrow_period <>", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodGreaterThan(String value) {
            addCriterion("borrow_period >", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_period >=", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLessThan(String value) {
            addCriterion("borrow_period <", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLessThanOrEqualTo(String value) {
            addCriterion("borrow_period <=", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodLike(String value) {
            addCriterion("borrow_period like", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotLike(String value) {
            addCriterion("borrow_period not like", value, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodIn(List<String> values) {
            addCriterion("borrow_period in", values, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotIn(List<String> values) {
            addCriterion("borrow_period not in", values, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodBetween(String value1, String value2) {
            addCriterion("borrow_period between", value1, value2, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowPeriodNotBetween(String value1, String value2) {
            addCriterion("borrow_period not between", value1, value2, "borrowPeriod");
            return (Criteria) this;
        }

        public Criteria andBorrowAprIsNull() {
            addCriterion("borrow_apr is null");
            return (Criteria) this;
        }

        public Criteria andBorrowAprIsNotNull() {
            addCriterion("borrow_apr is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowAprEqualTo(String value) {
            addCriterion("borrow_apr =", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotEqualTo(String value) {
            addCriterion("borrow_apr <>", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprGreaterThan(String value) {
            addCriterion("borrow_apr >", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_apr >=", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprLessThan(String value) {
            addCriterion("borrow_apr <", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprLessThanOrEqualTo(String value) {
            addCriterion("borrow_apr <=", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprLike(String value) {
            addCriterion("borrow_apr like", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotLike(String value) {
            addCriterion("borrow_apr not like", value, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprIn(List<String> values) {
            addCriterion("borrow_apr in", values, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotIn(List<String> values) {
            addCriterion("borrow_apr not in", values, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprBetween(String value1, String value2) {
            addCriterion("borrow_apr between", value1, value2, "borrowApr");
            return (Criteria) this;
        }

        public Criteria andBorrowAprNotBetween(String value1, String value2) {
            addCriterion("borrow_apr not between", value1, value2, "borrowApr");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("`status` like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("`status` not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeIsNull() {
            addCriterion("borrow_full_time is null");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeIsNotNull() {
            addCriterion("borrow_full_time is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeEqualTo(String value) {
            addCriterion("borrow_full_time =", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeNotEqualTo(String value) {
            addCriterion("borrow_full_time <>", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeGreaterThan(String value) {
            addCriterion("borrow_full_time >", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeGreaterThanOrEqualTo(String value) {
            addCriterion("borrow_full_time >=", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeLessThan(String value) {
            addCriterion("borrow_full_time <", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeLessThanOrEqualTo(String value) {
            addCriterion("borrow_full_time <=", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeLike(String value) {
            addCriterion("borrow_full_time like", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeNotLike(String value) {
            addCriterion("borrow_full_time not like", value, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeIn(List<String> values) {
            addCriterion("borrow_full_time in", values, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeNotIn(List<String> values) {
            addCriterion("borrow_full_time not in", values, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeBetween(String value1, String value2) {
            addCriterion("borrow_full_time between", value1, value2, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andBorrowFullTimeNotBetween(String value1, String value2) {
            addCriterion("borrow_full_time not between", value1, value2, "borrowFullTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeIsNull() {
            addCriterion("recover_last_time is null");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeIsNotNull() {
            addCriterion("recover_last_time is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeEqualTo(String value) {
            addCriterion("recover_last_time =", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeNotEqualTo(String value) {
            addCriterion("recover_last_time <>", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeGreaterThan(String value) {
            addCriterion("recover_last_time >", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeGreaterThanOrEqualTo(String value) {
            addCriterion("recover_last_time >=", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeLessThan(String value) {
            addCriterion("recover_last_time <", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeLessThanOrEqualTo(String value) {
            addCriterion("recover_last_time <=", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeLike(String value) {
            addCriterion("recover_last_time like", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeNotLike(String value) {
            addCriterion("recover_last_time not like", value, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeIn(List<String> values) {
            addCriterion("recover_last_time in", values, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeNotIn(List<String> values) {
            addCriterion("recover_last_time not in", values, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeBetween(String value1, String value2) {
            addCriterion("recover_last_time between", value1, value2, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andRecoverLastTimeNotBetween(String value1, String value2) {
            addCriterion("recover_last_time not between", value1, value2, "recoverLastTime");
            return (Criteria) this;
        }

        public Criteria andBailNumIsNull() {
            addCriterion("bail_num is null");
            return (Criteria) this;
        }

        public Criteria andBailNumIsNotNull() {
            addCriterion("bail_num is not null");
            return (Criteria) this;
        }

        public Criteria andBailNumEqualTo(Long value) {
            addCriterion("bail_num =", value, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumNotEqualTo(Long value) {
            addCriterion("bail_num <>", value, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumGreaterThan(Long value) {
            addCriterion("bail_num >", value, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumGreaterThanOrEqualTo(Long value) {
            addCriterion("bail_num >=", value, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumLessThan(Long value) {
            addCriterion("bail_num <", value, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumLessThanOrEqualTo(Long value) {
            addCriterion("bail_num <=", value, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumIn(List<Long> values) {
            addCriterion("bail_num in", values, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumNotIn(List<Long> values) {
            addCriterion("bail_num not in", values, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumBetween(Long value1, Long value2) {
            addCriterion("bail_num between", value1, value2, "bailNum");
            return (Criteria) this;
        }

        public Criteria andBailNumNotBetween(Long value1, Long value2) {
            addCriterion("bail_num not between", value1, value2, "bailNum");
            return (Criteria) this;
        }

        public Criteria andOperaterUidIsNull() {
            addCriterion("operater_uid is null");
            return (Criteria) this;
        }

        public Criteria andOperaterUidIsNotNull() {
            addCriterion("operater_uid is not null");
            return (Criteria) this;
        }

        public Criteria andOperaterUidEqualTo(Integer value) {
            addCriterion("operater_uid =", value, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidNotEqualTo(Integer value) {
            addCriterion("operater_uid <>", value, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidGreaterThan(Integer value) {
            addCriterion("operater_uid >", value, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("operater_uid >=", value, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidLessThan(Integer value) {
            addCriterion("operater_uid <", value, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidLessThanOrEqualTo(Integer value) {
            addCriterion("operater_uid <=", value, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidIn(List<Integer> values) {
            addCriterion("operater_uid in", values, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidNotIn(List<Integer> values) {
            addCriterion("operater_uid not in", values, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidBetween(Integer value1, Integer value2) {
            addCriterion("operater_uid between", value1, value2, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUidNotBetween(Integer value1, Integer value2) {
            addCriterion("operater_uid not between", value1, value2, "operaterUid");
            return (Criteria) this;
        }

        public Criteria andOperaterUserIsNull() {
            addCriterion("operater_user is null");
            return (Criteria) this;
        }

        public Criteria andOperaterUserIsNotNull() {
            addCriterion("operater_user is not null");
            return (Criteria) this;
        }

        public Criteria andOperaterUserEqualTo(String value) {
            addCriterion("operater_user =", value, "operaterUser");
            return (Criteria) this;
        }

        public Criteria andOperaterUserNotEqualTo(String value) {
            addCriterion("operater_user <>", value, "operaterUser");
            return (Criteria) this;
        }

        public Criteria andOperaterUserGreaterThan(String value) {
            addCriterion("operater_user >", value, "operaterUser");
            return (Criteria) this;
        }

        public Criteria andOperaterUserGreaterThanOrEqualTo(String value) {
            addCriterion("operater_user >=", value, "operaterUser");
            return (Criteria) this;
        }

        public Criteria andOperaterUserLessThan(String value) {
            addCriterion("operater_user <", value, "operaterUser");
            return (Criteria) this;
        }

        public Criteria andOperaterUserLessThanOrEqualTo(String value) {
            addCriterion("operater_user <=", value, "operaterUser");
            return (Criteria) this;
        }

        public Criteria andOperaterUserLike(String value) {
            addCriterion("operater_user like", value, "operaterUser");
            return (Criteria) this;
        }

        public Criteria andOperaterUserNotLike(String value) {
            addCriterion("operater_user not like", value, "operaterUser");
            return (Criteria) this;
        }

        public Criteria andOperaterUserIn(List<String> values) {
            addCriterion("operater_user in", values, "operaterUser");
            return (Criteria) this;
        }

        public Criteria andOperaterUserNotIn(List<String> values) {
            addCriterion("operater_user not in", values, "operaterUser");
            return (Criteria) this;
        }

        public Criteria andOperaterUserBetween(String value1, String value2) {
            addCriterion("operater_user between", value1, value2, "operaterUser");
            return (Criteria) this;
        }

        public Criteria andOperaterUserNotBetween(String value1, String value2) {
            addCriterion("operater_user not between", value1, value2, "operaterUser");
            return (Criteria) this;
        }

        public Criteria andOperaterTimeIsNull() {
            addCriterion("operater_time is null");
            return (Criteria) this;
        }

        public Criteria andOperaterTimeIsNotNull() {
            addCriterion("operater_time is not null");
            return (Criteria) this;
        }

        public Criteria andOperaterTimeEqualTo(Integer value) {
            addCriterion("operater_time =", value, "operaterTime");
            return (Criteria) this;
        }

        public Criteria andOperaterTimeNotEqualTo(Integer value) {
            addCriterion("operater_time <>", value, "operaterTime");
            return (Criteria) this;
        }

        public Criteria andOperaterTimeGreaterThan(Integer value) {
            addCriterion("operater_time >", value, "operaterTime");
            return (Criteria) this;
        }

        public Criteria andOperaterTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("operater_time >=", value, "operaterTime");
            return (Criteria) this;
        }

        public Criteria andOperaterTimeLessThan(Integer value) {
            addCriterion("operater_time <", value, "operaterTime");
            return (Criteria) this;
        }

        public Criteria andOperaterTimeLessThanOrEqualTo(Integer value) {
            addCriterion("operater_time <=", value, "operaterTime");
            return (Criteria) this;
        }

        public Criteria andOperaterTimeIn(List<Integer> values) {
            addCriterion("operater_time in", values, "operaterTime");
            return (Criteria) this;
        }

        public Criteria andOperaterTimeNotIn(List<Integer> values) {
            addCriterion("operater_time not in", values, "operaterTime");
            return (Criteria) this;
        }

        public Criteria andOperaterTimeBetween(Integer value1, Integer value2) {
            addCriterion("operater_time between", value1, value2, "operaterTime");
            return (Criteria) this;
        }

        public Criteria andOperaterTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("operater_time not between", value1, value2, "operaterTime");
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