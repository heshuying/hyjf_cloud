package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminMenuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public AdminMenuExample() {
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

        public Criteria andMenuUuidIsNull() {
            addCriterion("menu_uuid is null");
            return (Criteria) this;
        }

        public Criteria andMenuUuidIsNotNull() {
            addCriterion("menu_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andMenuUuidEqualTo(String value) {
            addCriterion("menu_uuid =", value, "menuUuid");
            return (Criteria) this;
        }

        public Criteria andMenuUuidNotEqualTo(String value) {
            addCriterion("menu_uuid <>", value, "menuUuid");
            return (Criteria) this;
        }

        public Criteria andMenuUuidGreaterThan(String value) {
            addCriterion("menu_uuid >", value, "menuUuid");
            return (Criteria) this;
        }

        public Criteria andMenuUuidGreaterThanOrEqualTo(String value) {
            addCriterion("menu_uuid >=", value, "menuUuid");
            return (Criteria) this;
        }

        public Criteria andMenuUuidLessThan(String value) {
            addCriterion("menu_uuid <", value, "menuUuid");
            return (Criteria) this;
        }

        public Criteria andMenuUuidLessThanOrEqualTo(String value) {
            addCriterion("menu_uuid <=", value, "menuUuid");
            return (Criteria) this;
        }

        public Criteria andMenuUuidLike(String value) {
            addCriterion("menu_uuid like", value, "menuUuid");
            return (Criteria) this;
        }

        public Criteria andMenuUuidNotLike(String value) {
            addCriterion("menu_uuid not like", value, "menuUuid");
            return (Criteria) this;
        }

        public Criteria andMenuUuidIn(List<String> values) {
            addCriterion("menu_uuid in", values, "menuUuid");
            return (Criteria) this;
        }

        public Criteria andMenuUuidNotIn(List<String> values) {
            addCriterion("menu_uuid not in", values, "menuUuid");
            return (Criteria) this;
        }

        public Criteria andMenuUuidBetween(String value1, String value2) {
            addCriterion("menu_uuid between", value1, value2, "menuUuid");
            return (Criteria) this;
        }

        public Criteria andMenuUuidNotBetween(String value1, String value2) {
            addCriterion("menu_uuid not between", value1, value2, "menuUuid");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidIsNull() {
            addCriterion("menu_puuid is null");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidIsNotNull() {
            addCriterion("menu_puuid is not null");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidEqualTo(String value) {
            addCriterion("menu_puuid =", value, "menuPuuid");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidNotEqualTo(String value) {
            addCriterion("menu_puuid <>", value, "menuPuuid");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidGreaterThan(String value) {
            addCriterion("menu_puuid >", value, "menuPuuid");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidGreaterThanOrEqualTo(String value) {
            addCriterion("menu_puuid >=", value, "menuPuuid");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidLessThan(String value) {
            addCriterion("menu_puuid <", value, "menuPuuid");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidLessThanOrEqualTo(String value) {
            addCriterion("menu_puuid <=", value, "menuPuuid");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidLike(String value) {
            addCriterion("menu_puuid like", value, "menuPuuid");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidNotLike(String value) {
            addCriterion("menu_puuid not like", value, "menuPuuid");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidIn(List<String> values) {
            addCriterion("menu_puuid in", values, "menuPuuid");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidNotIn(List<String> values) {
            addCriterion("menu_puuid not in", values, "menuPuuid");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidBetween(String value1, String value2) {
            addCriterion("menu_puuid between", value1, value2, "menuPuuid");
            return (Criteria) this;
        }

        public Criteria andMenuPuuidNotBetween(String value1, String value2) {
            addCriterion("menu_puuid not between", value1, value2, "menuPuuid");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlIsNull() {
            addCriterion("menu_ctrl is null");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlIsNotNull() {
            addCriterion("menu_ctrl is not null");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlEqualTo(String value) {
            addCriterion("menu_ctrl =", value, "menuCtrl");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlNotEqualTo(String value) {
            addCriterion("menu_ctrl <>", value, "menuCtrl");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlGreaterThan(String value) {
            addCriterion("menu_ctrl >", value, "menuCtrl");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlGreaterThanOrEqualTo(String value) {
            addCriterion("menu_ctrl >=", value, "menuCtrl");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlLessThan(String value) {
            addCriterion("menu_ctrl <", value, "menuCtrl");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlLessThanOrEqualTo(String value) {
            addCriterion("menu_ctrl <=", value, "menuCtrl");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlLike(String value) {
            addCriterion("menu_ctrl like", value, "menuCtrl");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlNotLike(String value) {
            addCriterion("menu_ctrl not like", value, "menuCtrl");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlIn(List<String> values) {
            addCriterion("menu_ctrl in", values, "menuCtrl");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlNotIn(List<String> values) {
            addCriterion("menu_ctrl not in", values, "menuCtrl");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlBetween(String value1, String value2) {
            addCriterion("menu_ctrl between", value1, value2, "menuCtrl");
            return (Criteria) this;
        }

        public Criteria andMenuCtrlNotBetween(String value1, String value2) {
            addCriterion("menu_ctrl not between", value1, value2, "menuCtrl");
            return (Criteria) this;
        }

        public Criteria andMenuIconIsNull() {
            addCriterion("menu_icon is null");
            return (Criteria) this;
        }

        public Criteria andMenuIconIsNotNull() {
            addCriterion("menu_icon is not null");
            return (Criteria) this;
        }

        public Criteria andMenuIconEqualTo(String value) {
            addCriterion("menu_icon =", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconNotEqualTo(String value) {
            addCriterion("menu_icon <>", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconGreaterThan(String value) {
            addCriterion("menu_icon >", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconGreaterThanOrEqualTo(String value) {
            addCriterion("menu_icon >=", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconLessThan(String value) {
            addCriterion("menu_icon <", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconLessThanOrEqualTo(String value) {
            addCriterion("menu_icon <=", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconLike(String value) {
            addCriterion("menu_icon like", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconNotLike(String value) {
            addCriterion("menu_icon not like", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconIn(List<String> values) {
            addCriterion("menu_icon in", values, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconNotIn(List<String> values) {
            addCriterion("menu_icon not in", values, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconBetween(String value1, String value2) {
            addCriterion("menu_icon between", value1, value2, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconNotBetween(String value1, String value2) {
            addCriterion("menu_icon not between", value1, value2, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuNameIsNull() {
            addCriterion("menu_name is null");
            return (Criteria) this;
        }

        public Criteria andMenuNameIsNotNull() {
            addCriterion("menu_name is not null");
            return (Criteria) this;
        }

        public Criteria andMenuNameEqualTo(String value) {
            addCriterion("menu_name =", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotEqualTo(String value) {
            addCriterion("menu_name <>", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameGreaterThan(String value) {
            addCriterion("menu_name >", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameGreaterThanOrEqualTo(String value) {
            addCriterion("menu_name >=", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLessThan(String value) {
            addCriterion("menu_name <", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLessThanOrEqualTo(String value) {
            addCriterion("menu_name <=", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLike(String value) {
            addCriterion("menu_name like", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotLike(String value) {
            addCriterion("menu_name not like", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameIn(List<String> values) {
            addCriterion("menu_name in", values, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotIn(List<String> values) {
            addCriterion("menu_name not in", values, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameBetween(String value1, String value2) {
            addCriterion("menu_name between", value1, value2, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotBetween(String value1, String value2) {
            addCriterion("menu_name not between", value1, value2, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuSortIsNull() {
            addCriterion("menu_sort is null");
            return (Criteria) this;
        }

        public Criteria andMenuSortIsNotNull() {
            addCriterion("menu_sort is not null");
            return (Criteria) this;
        }

        public Criteria andMenuSortEqualTo(Integer value) {
            addCriterion("menu_sort =", value, "menuSort");
            return (Criteria) this;
        }

        public Criteria andMenuSortNotEqualTo(Integer value) {
            addCriterion("menu_sort <>", value, "menuSort");
            return (Criteria) this;
        }

        public Criteria andMenuSortGreaterThan(Integer value) {
            addCriterion("menu_sort >", value, "menuSort");
            return (Criteria) this;
        }

        public Criteria andMenuSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("menu_sort >=", value, "menuSort");
            return (Criteria) this;
        }

        public Criteria andMenuSortLessThan(Integer value) {
            addCriterion("menu_sort <", value, "menuSort");
            return (Criteria) this;
        }

        public Criteria andMenuSortLessThanOrEqualTo(Integer value) {
            addCriterion("menu_sort <=", value, "menuSort");
            return (Criteria) this;
        }

        public Criteria andMenuSortIn(List<Integer> values) {
            addCriterion("menu_sort in", values, "menuSort");
            return (Criteria) this;
        }

        public Criteria andMenuSortNotIn(List<Integer> values) {
            addCriterion("menu_sort not in", values, "menuSort");
            return (Criteria) this;
        }

        public Criteria andMenuSortBetween(Integer value1, Integer value2) {
            addCriterion("menu_sort between", value1, value2, "menuSort");
            return (Criteria) this;
        }

        public Criteria andMenuSortNotBetween(Integer value1, Integer value2) {
            addCriterion("menu_sort not between", value1, value2, "menuSort");
            return (Criteria) this;
        }

        public Criteria andMenuUrlIsNull() {
            addCriterion("menu_url is null");
            return (Criteria) this;
        }

        public Criteria andMenuUrlIsNotNull() {
            addCriterion("menu_url is not null");
            return (Criteria) this;
        }

        public Criteria andMenuUrlEqualTo(String value) {
            addCriterion("menu_url =", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlNotEqualTo(String value) {
            addCriterion("menu_url <>", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlGreaterThan(String value) {
            addCriterion("menu_url >", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlGreaterThanOrEqualTo(String value) {
            addCriterion("menu_url >=", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlLessThan(String value) {
            addCriterion("menu_url <", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlLessThanOrEqualTo(String value) {
            addCriterion("menu_url <=", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlLike(String value) {
            addCriterion("menu_url like", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlNotLike(String value) {
            addCriterion("menu_url not like", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlIn(List<String> values) {
            addCriterion("menu_url in", values, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlNotIn(List<String> values) {
            addCriterion("menu_url not in", values, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlBetween(String value1, String value2) {
            addCriterion("menu_url between", value1, value2, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlNotBetween(String value1, String value2) {
            addCriterion("menu_url not between", value1, value2, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuHideIsNull() {
            addCriterion("menu_hide is null");
            return (Criteria) this;
        }

        public Criteria andMenuHideIsNotNull() {
            addCriterion("menu_hide is not null");
            return (Criteria) this;
        }

        public Criteria andMenuHideEqualTo(Integer value) {
            addCriterion("menu_hide =", value, "menuHide");
            return (Criteria) this;
        }

        public Criteria andMenuHideNotEqualTo(Integer value) {
            addCriterion("menu_hide <>", value, "menuHide");
            return (Criteria) this;
        }

        public Criteria andMenuHideGreaterThan(Integer value) {
            addCriterion("menu_hide >", value, "menuHide");
            return (Criteria) this;
        }

        public Criteria andMenuHideGreaterThanOrEqualTo(Integer value) {
            addCriterion("menu_hide >=", value, "menuHide");
            return (Criteria) this;
        }

        public Criteria andMenuHideLessThan(Integer value) {
            addCriterion("menu_hide <", value, "menuHide");
            return (Criteria) this;
        }

        public Criteria andMenuHideLessThanOrEqualTo(Integer value) {
            addCriterion("menu_hide <=", value, "menuHide");
            return (Criteria) this;
        }

        public Criteria andMenuHideIn(List<Integer> values) {
            addCriterion("menu_hide in", values, "menuHide");
            return (Criteria) this;
        }

        public Criteria andMenuHideNotIn(List<Integer> values) {
            addCriterion("menu_hide not in", values, "menuHide");
            return (Criteria) this;
        }

        public Criteria andMenuHideBetween(Integer value1, Integer value2) {
            addCriterion("menu_hide between", value1, value2, "menuHide");
            return (Criteria) this;
        }

        public Criteria andMenuHideNotBetween(Integer value1, Integer value2) {
            addCriterion("menu_hide not between", value1, value2, "menuHide");
            return (Criteria) this;
        }

        public Criteria andMenuTipIsNull() {
            addCriterion("menu_tip is null");
            return (Criteria) this;
        }

        public Criteria andMenuTipIsNotNull() {
            addCriterion("menu_tip is not null");
            return (Criteria) this;
        }

        public Criteria andMenuTipEqualTo(String value) {
            addCriterion("menu_tip =", value, "menuTip");
            return (Criteria) this;
        }

        public Criteria andMenuTipNotEqualTo(String value) {
            addCriterion("menu_tip <>", value, "menuTip");
            return (Criteria) this;
        }

        public Criteria andMenuTipGreaterThan(String value) {
            addCriterion("menu_tip >", value, "menuTip");
            return (Criteria) this;
        }

        public Criteria andMenuTipGreaterThanOrEqualTo(String value) {
            addCriterion("menu_tip >=", value, "menuTip");
            return (Criteria) this;
        }

        public Criteria andMenuTipLessThan(String value) {
            addCriterion("menu_tip <", value, "menuTip");
            return (Criteria) this;
        }

        public Criteria andMenuTipLessThanOrEqualTo(String value) {
            addCriterion("menu_tip <=", value, "menuTip");
            return (Criteria) this;
        }

        public Criteria andMenuTipLike(String value) {
            addCriterion("menu_tip like", value, "menuTip");
            return (Criteria) this;
        }

        public Criteria andMenuTipNotLike(String value) {
            addCriterion("menu_tip not like", value, "menuTip");
            return (Criteria) this;
        }

        public Criteria andMenuTipIn(List<String> values) {
            addCriterion("menu_tip in", values, "menuTip");
            return (Criteria) this;
        }

        public Criteria andMenuTipNotIn(List<String> values) {
            addCriterion("menu_tip not in", values, "menuTip");
            return (Criteria) this;
        }

        public Criteria andMenuTipBetween(String value1, String value2) {
            addCriterion("menu_tip between", value1, value2, "menuTip");
            return (Criteria) this;
        }

        public Criteria andMenuTipNotBetween(String value1, String value2) {
            addCriterion("menu_tip not between", value1, value2, "menuTip");
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