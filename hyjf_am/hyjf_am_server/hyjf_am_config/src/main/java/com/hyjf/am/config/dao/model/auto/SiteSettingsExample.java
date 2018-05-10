package com.hyjf.am.config.dao.model.auto;

import java.util.ArrayList;
import java.util.List;

public class SiteSettingsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public SiteSettingsExample() {
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

        public Criteria andCompanyIsNull() {
            addCriterion("company is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            addCriterion("company is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(String value) {
            addCriterion("company =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(String value) {
            addCriterion("company <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(String value) {
            addCriterion("company >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("company >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(String value) {
            addCriterion("company <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(String value) {
            addCriterion("company <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLike(String value) {
            addCriterion("company like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotLike(String value) {
            addCriterion("company not like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(List<String> values) {
            addCriterion("company in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(List<String> values) {
            addCriterion("company not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(String value1, String value2) {
            addCriterion("company between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(String value1, String value2) {
            addCriterion("company not between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNull() {
            addCriterion("site_name is null");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNotNull() {
            addCriterion("site_name is not null");
            return (Criteria) this;
        }

        public Criteria andSiteNameEqualTo(String value) {
            addCriterion("site_name =", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotEqualTo(String value) {
            addCriterion("site_name <>", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThan(String value) {
            addCriterion("site_name >", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThanOrEqualTo(String value) {
            addCriterion("site_name >=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThan(String value) {
            addCriterion("site_name <", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThanOrEqualTo(String value) {
            addCriterion("site_name <=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLike(String value) {
            addCriterion("site_name like", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotLike(String value) {
            addCriterion("site_name not like", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameIn(List<String> values) {
            addCriterion("site_name in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotIn(List<String> values) {
            addCriterion("site_name not in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameBetween(String value1, String value2) {
            addCriterion("site_name between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotBetween(String value1, String value2) {
            addCriterion("site_name not between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteDomainIsNull() {
            addCriterion("site_domain is null");
            return (Criteria) this;
        }

        public Criteria andSiteDomainIsNotNull() {
            addCriterion("site_domain is not null");
            return (Criteria) this;
        }

        public Criteria andSiteDomainEqualTo(String value) {
            addCriterion("site_domain =", value, "siteDomain");
            return (Criteria) this;
        }

        public Criteria andSiteDomainNotEqualTo(String value) {
            addCriterion("site_domain <>", value, "siteDomain");
            return (Criteria) this;
        }

        public Criteria andSiteDomainGreaterThan(String value) {
            addCriterion("site_domain >", value, "siteDomain");
            return (Criteria) this;
        }

        public Criteria andSiteDomainGreaterThanOrEqualTo(String value) {
            addCriterion("site_domain >=", value, "siteDomain");
            return (Criteria) this;
        }

        public Criteria andSiteDomainLessThan(String value) {
            addCriterion("site_domain <", value, "siteDomain");
            return (Criteria) this;
        }

        public Criteria andSiteDomainLessThanOrEqualTo(String value) {
            addCriterion("site_domain <=", value, "siteDomain");
            return (Criteria) this;
        }

        public Criteria andSiteDomainLike(String value) {
            addCriterion("site_domain like", value, "siteDomain");
            return (Criteria) this;
        }

        public Criteria andSiteDomainNotLike(String value) {
            addCriterion("site_domain not like", value, "siteDomain");
            return (Criteria) this;
        }

        public Criteria andSiteDomainIn(List<String> values) {
            addCriterion("site_domain in", values, "siteDomain");
            return (Criteria) this;
        }

        public Criteria andSiteDomainNotIn(List<String> values) {
            addCriterion("site_domain not in", values, "siteDomain");
            return (Criteria) this;
        }

        public Criteria andSiteDomainBetween(String value1, String value2) {
            addCriterion("site_domain between", value1, value2, "siteDomain");
            return (Criteria) this;
        }

        public Criteria andSiteDomainNotBetween(String value1, String value2) {
            addCriterion("site_domain not between", value1, value2, "siteDomain");
            return (Criteria) this;
        }

        public Criteria andSiteLogoIsNull() {
            addCriterion("site_logo is null");
            return (Criteria) this;
        }

        public Criteria andSiteLogoIsNotNull() {
            addCriterion("site_logo is not null");
            return (Criteria) this;
        }

        public Criteria andSiteLogoEqualTo(String value) {
            addCriterion("site_logo =", value, "siteLogo");
            return (Criteria) this;
        }

        public Criteria andSiteLogoNotEqualTo(String value) {
            addCriterion("site_logo <>", value, "siteLogo");
            return (Criteria) this;
        }

        public Criteria andSiteLogoGreaterThan(String value) {
            addCriterion("site_logo >", value, "siteLogo");
            return (Criteria) this;
        }

        public Criteria andSiteLogoGreaterThanOrEqualTo(String value) {
            addCriterion("site_logo >=", value, "siteLogo");
            return (Criteria) this;
        }

        public Criteria andSiteLogoLessThan(String value) {
            addCriterion("site_logo <", value, "siteLogo");
            return (Criteria) this;
        }

        public Criteria andSiteLogoLessThanOrEqualTo(String value) {
            addCriterion("site_logo <=", value, "siteLogo");
            return (Criteria) this;
        }

        public Criteria andSiteLogoLike(String value) {
            addCriterion("site_logo like", value, "siteLogo");
            return (Criteria) this;
        }

        public Criteria andSiteLogoNotLike(String value) {
            addCriterion("site_logo not like", value, "siteLogo");
            return (Criteria) this;
        }

        public Criteria andSiteLogoIn(List<String> values) {
            addCriterion("site_logo in", values, "siteLogo");
            return (Criteria) this;
        }

        public Criteria andSiteLogoNotIn(List<String> values) {
            addCriterion("site_logo not in", values, "siteLogo");
            return (Criteria) this;
        }

        public Criteria andSiteLogoBetween(String value1, String value2) {
            addCriterion("site_logo between", value1, value2, "siteLogo");
            return (Criteria) this;
        }

        public Criteria andSiteLogoNotBetween(String value1, String value2) {
            addCriterion("site_logo not between", value1, value2, "siteLogo");
            return (Criteria) this;
        }

        public Criteria andSiteIcpIsNull() {
            addCriterion("site_icp is null");
            return (Criteria) this;
        }

        public Criteria andSiteIcpIsNotNull() {
            addCriterion("site_icp is not null");
            return (Criteria) this;
        }

        public Criteria andSiteIcpEqualTo(String value) {
            addCriterion("site_icp =", value, "siteIcp");
            return (Criteria) this;
        }

        public Criteria andSiteIcpNotEqualTo(String value) {
            addCriterion("site_icp <>", value, "siteIcp");
            return (Criteria) this;
        }

        public Criteria andSiteIcpGreaterThan(String value) {
            addCriterion("site_icp >", value, "siteIcp");
            return (Criteria) this;
        }

        public Criteria andSiteIcpGreaterThanOrEqualTo(String value) {
            addCriterion("site_icp >=", value, "siteIcp");
            return (Criteria) this;
        }

        public Criteria andSiteIcpLessThan(String value) {
            addCriterion("site_icp <", value, "siteIcp");
            return (Criteria) this;
        }

        public Criteria andSiteIcpLessThanOrEqualTo(String value) {
            addCriterion("site_icp <=", value, "siteIcp");
            return (Criteria) this;
        }

        public Criteria andSiteIcpLike(String value) {
            addCriterion("site_icp like", value, "siteIcp");
            return (Criteria) this;
        }

        public Criteria andSiteIcpNotLike(String value) {
            addCriterion("site_icp not like", value, "siteIcp");
            return (Criteria) this;
        }

        public Criteria andSiteIcpIn(List<String> values) {
            addCriterion("site_icp in", values, "siteIcp");
            return (Criteria) this;
        }

        public Criteria andSiteIcpNotIn(List<String> values) {
            addCriterion("site_icp not in", values, "siteIcp");
            return (Criteria) this;
        }

        public Criteria andSiteIcpBetween(String value1, String value2) {
            addCriterion("site_icp between", value1, value2, "siteIcp");
            return (Criteria) this;
        }

        public Criteria andSiteIcpNotBetween(String value1, String value2) {
            addCriterion("site_icp not between", value1, value2, "siteIcp");
            return (Criteria) this;
        }

        public Criteria andSiteStatsIsNull() {
            addCriterion("site_stats is null");
            return (Criteria) this;
        }

        public Criteria andSiteStatsIsNotNull() {
            addCriterion("site_stats is not null");
            return (Criteria) this;
        }

        public Criteria andSiteStatsEqualTo(String value) {
            addCriterion("site_stats =", value, "siteStats");
            return (Criteria) this;
        }

        public Criteria andSiteStatsNotEqualTo(String value) {
            addCriterion("site_stats <>", value, "siteStats");
            return (Criteria) this;
        }

        public Criteria andSiteStatsGreaterThan(String value) {
            addCriterion("site_stats >", value, "siteStats");
            return (Criteria) this;
        }

        public Criteria andSiteStatsGreaterThanOrEqualTo(String value) {
            addCriterion("site_stats >=", value, "siteStats");
            return (Criteria) this;
        }

        public Criteria andSiteStatsLessThan(String value) {
            addCriterion("site_stats <", value, "siteStats");
            return (Criteria) this;
        }

        public Criteria andSiteStatsLessThanOrEqualTo(String value) {
            addCriterion("site_stats <=", value, "siteStats");
            return (Criteria) this;
        }

        public Criteria andSiteStatsLike(String value) {
            addCriterion("site_stats like", value, "siteStats");
            return (Criteria) this;
        }

        public Criteria andSiteStatsNotLike(String value) {
            addCriterion("site_stats not like", value, "siteStats");
            return (Criteria) this;
        }

        public Criteria andSiteStatsIn(List<String> values) {
            addCriterion("site_stats in", values, "siteStats");
            return (Criteria) this;
        }

        public Criteria andSiteStatsNotIn(List<String> values) {
            addCriterion("site_stats not in", values, "siteStats");
            return (Criteria) this;
        }

        public Criteria andSiteStatsBetween(String value1, String value2) {
            addCriterion("site_stats between", value1, value2, "siteStats");
            return (Criteria) this;
        }

        public Criteria andSiteStatsNotBetween(String value1, String value2) {
            addCriterion("site_stats not between", value1, value2, "siteStats");
            return (Criteria) this;
        }

        public Criteria andSiteFooterIsNull() {
            addCriterion("site_footer is null");
            return (Criteria) this;
        }

        public Criteria andSiteFooterIsNotNull() {
            addCriterion("site_footer is not null");
            return (Criteria) this;
        }

        public Criteria andSiteFooterEqualTo(String value) {
            addCriterion("site_footer =", value, "siteFooter");
            return (Criteria) this;
        }

        public Criteria andSiteFooterNotEqualTo(String value) {
            addCriterion("site_footer <>", value, "siteFooter");
            return (Criteria) this;
        }

        public Criteria andSiteFooterGreaterThan(String value) {
            addCriterion("site_footer >", value, "siteFooter");
            return (Criteria) this;
        }

        public Criteria andSiteFooterGreaterThanOrEqualTo(String value) {
            addCriterion("site_footer >=", value, "siteFooter");
            return (Criteria) this;
        }

        public Criteria andSiteFooterLessThan(String value) {
            addCriterion("site_footer <", value, "siteFooter");
            return (Criteria) this;
        }

        public Criteria andSiteFooterLessThanOrEqualTo(String value) {
            addCriterion("site_footer <=", value, "siteFooter");
            return (Criteria) this;
        }

        public Criteria andSiteFooterLike(String value) {
            addCriterion("site_footer like", value, "siteFooter");
            return (Criteria) this;
        }

        public Criteria andSiteFooterNotLike(String value) {
            addCriterion("site_footer not like", value, "siteFooter");
            return (Criteria) this;
        }

        public Criteria andSiteFooterIn(List<String> values) {
            addCriterion("site_footer in", values, "siteFooter");
            return (Criteria) this;
        }

        public Criteria andSiteFooterNotIn(List<String> values) {
            addCriterion("site_footer not in", values, "siteFooter");
            return (Criteria) this;
        }

        public Criteria andSiteFooterBetween(String value1, String value2) {
            addCriterion("site_footer between", value1, value2, "siteFooter");
            return (Criteria) this;
        }

        public Criteria andSiteFooterNotBetween(String value1, String value2) {
            addCriterion("site_footer not between", value1, value2, "siteFooter");
            return (Criteria) this;
        }

        public Criteria andSiteStatusIsNull() {
            addCriterion("site_status is null");
            return (Criteria) this;
        }

        public Criteria andSiteStatusIsNotNull() {
            addCriterion("site_status is not null");
            return (Criteria) this;
        }

        public Criteria andSiteStatusEqualTo(Boolean value) {
            addCriterion("site_status =", value, "siteStatus");
            return (Criteria) this;
        }

        public Criteria andSiteStatusNotEqualTo(Boolean value) {
            addCriterion("site_status <>", value, "siteStatus");
            return (Criteria) this;
        }

        public Criteria andSiteStatusGreaterThan(Boolean value) {
            addCriterion("site_status >", value, "siteStatus");
            return (Criteria) this;
        }

        public Criteria andSiteStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("site_status >=", value, "siteStatus");
            return (Criteria) this;
        }

        public Criteria andSiteStatusLessThan(Boolean value) {
            addCriterion("site_status <", value, "siteStatus");
            return (Criteria) this;
        }

        public Criteria andSiteStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("site_status <=", value, "siteStatus");
            return (Criteria) this;
        }

        public Criteria andSiteStatusIn(List<Boolean> values) {
            addCriterion("site_status in", values, "siteStatus");
            return (Criteria) this;
        }

        public Criteria andSiteStatusNotIn(List<Boolean> values) {
            addCriterion("site_status not in", values, "siteStatus");
            return (Criteria) this;
        }

        public Criteria andSiteStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("site_status between", value1, value2, "siteStatus");
            return (Criteria) this;
        }

        public Criteria andSiteStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("site_status not between", value1, value2, "siteStatus");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonIsNull() {
            addCriterion("site_close_reason is null");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonIsNotNull() {
            addCriterion("site_close_reason is not null");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonEqualTo(String value) {
            addCriterion("site_close_reason =", value, "siteCloseReason");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonNotEqualTo(String value) {
            addCriterion("site_close_reason <>", value, "siteCloseReason");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonGreaterThan(String value) {
            addCriterion("site_close_reason >", value, "siteCloseReason");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonGreaterThanOrEqualTo(String value) {
            addCriterion("site_close_reason >=", value, "siteCloseReason");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonLessThan(String value) {
            addCriterion("site_close_reason <", value, "siteCloseReason");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonLessThanOrEqualTo(String value) {
            addCriterion("site_close_reason <=", value, "siteCloseReason");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonLike(String value) {
            addCriterion("site_close_reason like", value, "siteCloseReason");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonNotLike(String value) {
            addCriterion("site_close_reason not like", value, "siteCloseReason");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonIn(List<String> values) {
            addCriterion("site_close_reason in", values, "siteCloseReason");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonNotIn(List<String> values) {
            addCriterion("site_close_reason not in", values, "siteCloseReason");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonBetween(String value1, String value2) {
            addCriterion("site_close_reason between", value1, value2, "siteCloseReason");
            return (Criteria) this;
        }

        public Criteria andSiteCloseReasonNotBetween(String value1, String value2) {
            addCriterion("site_close_reason not between", value1, value2, "siteCloseReason");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordIsNull() {
            addCriterion("site_keyword is null");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordIsNotNull() {
            addCriterion("site_keyword is not null");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordEqualTo(String value) {
            addCriterion("site_keyword =", value, "siteKeyword");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordNotEqualTo(String value) {
            addCriterion("site_keyword <>", value, "siteKeyword");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordGreaterThan(String value) {
            addCriterion("site_keyword >", value, "siteKeyword");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordGreaterThanOrEqualTo(String value) {
            addCriterion("site_keyword >=", value, "siteKeyword");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordLessThan(String value) {
            addCriterion("site_keyword <", value, "siteKeyword");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordLessThanOrEqualTo(String value) {
            addCriterion("site_keyword <=", value, "siteKeyword");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordLike(String value) {
            addCriterion("site_keyword like", value, "siteKeyword");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordNotLike(String value) {
            addCriterion("site_keyword not like", value, "siteKeyword");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordIn(List<String> values) {
            addCriterion("site_keyword in", values, "siteKeyword");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordNotIn(List<String> values) {
            addCriterion("site_keyword not in", values, "siteKeyword");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordBetween(String value1, String value2) {
            addCriterion("site_keyword between", value1, value2, "siteKeyword");
            return (Criteria) this;
        }

        public Criteria andSiteKeywordNotBetween(String value1, String value2) {
            addCriterion("site_keyword not between", value1, value2, "siteKeyword");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionIsNull() {
            addCriterion("site_description is null");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionIsNotNull() {
            addCriterion("site_description is not null");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionEqualTo(String value) {
            addCriterion("site_description =", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionNotEqualTo(String value) {
            addCriterion("site_description <>", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionGreaterThan(String value) {
            addCriterion("site_description >", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("site_description >=", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionLessThan(String value) {
            addCriterion("site_description <", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionLessThanOrEqualTo(String value) {
            addCriterion("site_description <=", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionLike(String value) {
            addCriterion("site_description like", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionNotLike(String value) {
            addCriterion("site_description not like", value, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionIn(List<String> values) {
            addCriterion("site_description in", values, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionNotIn(List<String> values) {
            addCriterion("site_description not in", values, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionBetween(String value1, String value2) {
            addCriterion("site_description between", value1, value2, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteDescriptionNotBetween(String value1, String value2) {
            addCriterion("site_description not between", value1, value2, "siteDescription");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathIsNull() {
            addCriterion("site_theme_path is null");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathIsNotNull() {
            addCriterion("site_theme_path is not null");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathEqualTo(String value) {
            addCriterion("site_theme_path =", value, "siteThemePath");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathNotEqualTo(String value) {
            addCriterion("site_theme_path <>", value, "siteThemePath");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathGreaterThan(String value) {
            addCriterion("site_theme_path >", value, "siteThemePath");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathGreaterThanOrEqualTo(String value) {
            addCriterion("site_theme_path >=", value, "siteThemePath");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathLessThan(String value) {
            addCriterion("site_theme_path <", value, "siteThemePath");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathLessThanOrEqualTo(String value) {
            addCriterion("site_theme_path <=", value, "siteThemePath");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathLike(String value) {
            addCriterion("site_theme_path like", value, "siteThemePath");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathNotLike(String value) {
            addCriterion("site_theme_path not like", value, "siteThemePath");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathIn(List<String> values) {
            addCriterion("site_theme_path in", values, "siteThemePath");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathNotIn(List<String> values) {
            addCriterion("site_theme_path not in", values, "siteThemePath");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathBetween(String value1, String value2) {
            addCriterion("site_theme_path between", value1, value2, "siteThemePath");
            return (Criteria) this;
        }

        public Criteria andSiteThemePathNotBetween(String value1, String value2) {
            addCriterion("site_theme_path not between", value1, value2, "siteThemePath");
            return (Criteria) this;
        }

        public Criteria andSiteThemeIsNull() {
            addCriterion("site_theme is null");
            return (Criteria) this;
        }

        public Criteria andSiteThemeIsNotNull() {
            addCriterion("site_theme is not null");
            return (Criteria) this;
        }

        public Criteria andSiteThemeEqualTo(String value) {
            addCriterion("site_theme =", value, "siteTheme");
            return (Criteria) this;
        }

        public Criteria andSiteThemeNotEqualTo(String value) {
            addCriterion("site_theme <>", value, "siteTheme");
            return (Criteria) this;
        }

        public Criteria andSiteThemeGreaterThan(String value) {
            addCriterion("site_theme >", value, "siteTheme");
            return (Criteria) this;
        }

        public Criteria andSiteThemeGreaterThanOrEqualTo(String value) {
            addCriterion("site_theme >=", value, "siteTheme");
            return (Criteria) this;
        }

        public Criteria andSiteThemeLessThan(String value) {
            addCriterion("site_theme <", value, "siteTheme");
            return (Criteria) this;
        }

        public Criteria andSiteThemeLessThanOrEqualTo(String value) {
            addCriterion("site_theme <=", value, "siteTheme");
            return (Criteria) this;
        }

        public Criteria andSiteThemeLike(String value) {
            addCriterion("site_theme like", value, "siteTheme");
            return (Criteria) this;
        }

        public Criteria andSiteThemeNotLike(String value) {
            addCriterion("site_theme not like", value, "siteTheme");
            return (Criteria) this;
        }

        public Criteria andSiteThemeIn(List<String> values) {
            addCriterion("site_theme in", values, "siteTheme");
            return (Criteria) this;
        }

        public Criteria andSiteThemeNotIn(List<String> values) {
            addCriterion("site_theme not in", values, "siteTheme");
            return (Criteria) this;
        }

        public Criteria andSiteThemeBetween(String value1, String value2) {
            addCriterion("site_theme between", value1, value2, "siteTheme");
            return (Criteria) this;
        }

        public Criteria andSiteThemeNotBetween(String value1, String value2) {
            addCriterion("site_theme not between", value1, value2, "siteTheme");
            return (Criteria) this;
        }

        public Criteria andSmtpServerIsNull() {
            addCriterion("smtp_server is null");
            return (Criteria) this;
        }

        public Criteria andSmtpServerIsNotNull() {
            addCriterion("smtp_server is not null");
            return (Criteria) this;
        }

        public Criteria andSmtpServerEqualTo(String value) {
            addCriterion("smtp_server =", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerNotEqualTo(String value) {
            addCriterion("smtp_server <>", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerGreaterThan(String value) {
            addCriterion("smtp_server >", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerGreaterThanOrEqualTo(String value) {
            addCriterion("smtp_server >=", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerLessThan(String value) {
            addCriterion("smtp_server <", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerLessThanOrEqualTo(String value) {
            addCriterion("smtp_server <=", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerLike(String value) {
            addCriterion("smtp_server like", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerNotLike(String value) {
            addCriterion("smtp_server not like", value, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerIn(List<String> values) {
            addCriterion("smtp_server in", values, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerNotIn(List<String> values) {
            addCriterion("smtp_server not in", values, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerBetween(String value1, String value2) {
            addCriterion("smtp_server between", value1, value2, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpServerNotBetween(String value1, String value2) {
            addCriterion("smtp_server not between", value1, value2, "smtpServer");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyIsNull() {
            addCriterion("smtp_reply is null");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyIsNotNull() {
            addCriterion("smtp_reply is not null");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyEqualTo(String value) {
            addCriterion("smtp_reply =", value, "smtpReply");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyNotEqualTo(String value) {
            addCriterion("smtp_reply <>", value, "smtpReply");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyGreaterThan(String value) {
            addCriterion("smtp_reply >", value, "smtpReply");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyGreaterThanOrEqualTo(String value) {
            addCriterion("smtp_reply >=", value, "smtpReply");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyLessThan(String value) {
            addCriterion("smtp_reply <", value, "smtpReply");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyLessThanOrEqualTo(String value) {
            addCriterion("smtp_reply <=", value, "smtpReply");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyLike(String value) {
            addCriterion("smtp_reply like", value, "smtpReply");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyNotLike(String value) {
            addCriterion("smtp_reply not like", value, "smtpReply");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyIn(List<String> values) {
            addCriterion("smtp_reply in", values, "smtpReply");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyNotIn(List<String> values) {
            addCriterion("smtp_reply not in", values, "smtpReply");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyBetween(String value1, String value2) {
            addCriterion("smtp_reply between", value1, value2, "smtpReply");
            return (Criteria) this;
        }

        public Criteria andSmtpReplyNotBetween(String value1, String value2) {
            addCriterion("smtp_reply not between", value1, value2, "smtpReply");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameIsNull() {
            addCriterion("smtp_username is null");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameIsNotNull() {
            addCriterion("smtp_username is not null");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameEqualTo(String value) {
            addCriterion("smtp_username =", value, "smtpUsername");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameNotEqualTo(String value) {
            addCriterion("smtp_username <>", value, "smtpUsername");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameGreaterThan(String value) {
            addCriterion("smtp_username >", value, "smtpUsername");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("smtp_username >=", value, "smtpUsername");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameLessThan(String value) {
            addCriterion("smtp_username <", value, "smtpUsername");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameLessThanOrEqualTo(String value) {
            addCriterion("smtp_username <=", value, "smtpUsername");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameLike(String value) {
            addCriterion("smtp_username like", value, "smtpUsername");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameNotLike(String value) {
            addCriterion("smtp_username not like", value, "smtpUsername");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameIn(List<String> values) {
            addCriterion("smtp_username in", values, "smtpUsername");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameNotIn(List<String> values) {
            addCriterion("smtp_username not in", values, "smtpUsername");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameBetween(String value1, String value2) {
            addCriterion("smtp_username between", value1, value2, "smtpUsername");
            return (Criteria) this;
        }

        public Criteria andSmtpUsernameNotBetween(String value1, String value2) {
            addCriterion("smtp_username not between", value1, value2, "smtpUsername");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordIsNull() {
            addCriterion("smtp_password is null");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordIsNotNull() {
            addCriterion("smtp_password is not null");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordEqualTo(String value) {
            addCriterion("smtp_password =", value, "smtpPassword");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordNotEqualTo(String value) {
            addCriterion("smtp_password <>", value, "smtpPassword");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordGreaterThan(String value) {
            addCriterion("smtp_password >", value, "smtpPassword");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("smtp_password >=", value, "smtpPassword");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordLessThan(String value) {
            addCriterion("smtp_password <", value, "smtpPassword");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordLessThanOrEqualTo(String value) {
            addCriterion("smtp_password <=", value, "smtpPassword");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordLike(String value) {
            addCriterion("smtp_password like", value, "smtpPassword");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordNotLike(String value) {
            addCriterion("smtp_password not like", value, "smtpPassword");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordIn(List<String> values) {
            addCriterion("smtp_password in", values, "smtpPassword");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordNotIn(List<String> values) {
            addCriterion("smtp_password not in", values, "smtpPassword");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordBetween(String value1, String value2) {
            addCriterion("smtp_password between", value1, value2, "smtpPassword");
            return (Criteria) this;
        }

        public Criteria andSmtpPasswordNotBetween(String value1, String value2) {
            addCriterion("smtp_password not between", value1, value2, "smtpPassword");
            return (Criteria) this;
        }

        public Criteria andSmtpVerifyIsNull() {
            addCriterion("smtp_verify is null");
            return (Criteria) this;
        }

        public Criteria andSmtpVerifyIsNotNull() {
            addCriterion("smtp_verify is not null");
            return (Criteria) this;
        }

        public Criteria andSmtpVerifyEqualTo(Integer value) {
            addCriterion("smtp_verify =", value, "smtpVerify");
            return (Criteria) this;
        }

        public Criteria andSmtpVerifyNotEqualTo(Integer value) {
            addCriterion("smtp_verify <>", value, "smtpVerify");
            return (Criteria) this;
        }

        public Criteria andSmtpVerifyGreaterThan(Integer value) {
            addCriterion("smtp_verify >", value, "smtpVerify");
            return (Criteria) this;
        }

        public Criteria andSmtpVerifyGreaterThanOrEqualTo(Integer value) {
            addCriterion("smtp_verify >=", value, "smtpVerify");
            return (Criteria) this;
        }

        public Criteria andSmtpVerifyLessThan(Integer value) {
            addCriterion("smtp_verify <", value, "smtpVerify");
            return (Criteria) this;
        }

        public Criteria andSmtpVerifyLessThanOrEqualTo(Integer value) {
            addCriterion("smtp_verify <=", value, "smtpVerify");
            return (Criteria) this;
        }

        public Criteria andSmtpVerifyIn(List<Integer> values) {
            addCriterion("smtp_verify in", values, "smtpVerify");
            return (Criteria) this;
        }

        public Criteria andSmtpVerifyNotIn(List<Integer> values) {
            addCriterion("smtp_verify not in", values, "smtpVerify");
            return (Criteria) this;
        }

        public Criteria andSmtpVerifyBetween(Integer value1, Integer value2) {
            addCriterion("smtp_verify between", value1, value2, "smtpVerify");
            return (Criteria) this;
        }

        public Criteria andSmtpVerifyNotBetween(Integer value1, Integer value2) {
            addCriterion("smtp_verify not between", value1, value2, "smtpVerify");
            return (Criteria) this;
        }

        public Criteria andSmtpPortIsNull() {
            addCriterion("smtp_port is null");
            return (Criteria) this;
        }

        public Criteria andSmtpPortIsNotNull() {
            addCriterion("smtp_port is not null");
            return (Criteria) this;
        }

        public Criteria andSmtpPortEqualTo(String value) {
            addCriterion("smtp_port =", value, "smtpPort");
            return (Criteria) this;
        }

        public Criteria andSmtpPortNotEqualTo(String value) {
            addCriterion("smtp_port <>", value, "smtpPort");
            return (Criteria) this;
        }

        public Criteria andSmtpPortGreaterThan(String value) {
            addCriterion("smtp_port >", value, "smtpPort");
            return (Criteria) this;
        }

        public Criteria andSmtpPortGreaterThanOrEqualTo(String value) {
            addCriterion("smtp_port >=", value, "smtpPort");
            return (Criteria) this;
        }

        public Criteria andSmtpPortLessThan(String value) {
            addCriterion("smtp_port <", value, "smtpPort");
            return (Criteria) this;
        }

        public Criteria andSmtpPortLessThanOrEqualTo(String value) {
            addCriterion("smtp_port <=", value, "smtpPort");
            return (Criteria) this;
        }

        public Criteria andSmtpPortLike(String value) {
            addCriterion("smtp_port like", value, "smtpPort");
            return (Criteria) this;
        }

        public Criteria andSmtpPortNotLike(String value) {
            addCriterion("smtp_port not like", value, "smtpPort");
            return (Criteria) this;
        }

        public Criteria andSmtpPortIn(List<String> values) {
            addCriterion("smtp_port in", values, "smtpPort");
            return (Criteria) this;
        }

        public Criteria andSmtpPortNotIn(List<String> values) {
            addCriterion("smtp_port not in", values, "smtpPort");
            return (Criteria) this;
        }

        public Criteria andSmtpPortBetween(String value1, String value2) {
            addCriterion("smtp_port between", value1, value2, "smtpPort");
            return (Criteria) this;
        }

        public Criteria andSmtpPortNotBetween(String value1, String value2) {
            addCriterion("smtp_port not between", value1, value2, "smtpPort");
            return (Criteria) this;
        }

        public Criteria andSmtpSslIsNull() {
            addCriterion("smtp_ssl is null");
            return (Criteria) this;
        }

        public Criteria andSmtpSslIsNotNull() {
            addCriterion("smtp_ssl is not null");
            return (Criteria) this;
        }

        public Criteria andSmtpSslEqualTo(Integer value) {
            addCriterion("smtp_ssl =", value, "smtpSsl");
            return (Criteria) this;
        }

        public Criteria andSmtpSslNotEqualTo(Integer value) {
            addCriterion("smtp_ssl <>", value, "smtpSsl");
            return (Criteria) this;
        }

        public Criteria andSmtpSslGreaterThan(Integer value) {
            addCriterion("smtp_ssl >", value, "smtpSsl");
            return (Criteria) this;
        }

        public Criteria andSmtpSslGreaterThanOrEqualTo(Integer value) {
            addCriterion("smtp_ssl >=", value, "smtpSsl");
            return (Criteria) this;
        }

        public Criteria andSmtpSslLessThan(Integer value) {
            addCriterion("smtp_ssl <", value, "smtpSsl");
            return (Criteria) this;
        }

        public Criteria andSmtpSslLessThanOrEqualTo(Integer value) {
            addCriterion("smtp_ssl <=", value, "smtpSsl");
            return (Criteria) this;
        }

        public Criteria andSmtpSslIn(List<Integer> values) {
            addCriterion("smtp_ssl in", values, "smtpSsl");
            return (Criteria) this;
        }

        public Criteria andSmtpSslNotIn(List<Integer> values) {
            addCriterion("smtp_ssl not in", values, "smtpSsl");
            return (Criteria) this;
        }

        public Criteria andSmtpSslBetween(Integer value1, Integer value2) {
            addCriterion("smtp_ssl between", value1, value2, "smtpSsl");
            return (Criteria) this;
        }

        public Criteria andSmtpSslNotBetween(Integer value1, Integer value2) {
            addCriterion("smtp_ssl not between", value1, value2, "smtpSsl");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameIsNull() {
            addCriterion("smtp_from_name is null");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameIsNotNull() {
            addCriterion("smtp_from_name is not null");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameEqualTo(String value) {
            addCriterion("smtp_from_name =", value, "smtpFromName");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameNotEqualTo(String value) {
            addCriterion("smtp_from_name <>", value, "smtpFromName");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameGreaterThan(String value) {
            addCriterion("smtp_from_name >", value, "smtpFromName");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameGreaterThanOrEqualTo(String value) {
            addCriterion("smtp_from_name >=", value, "smtpFromName");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameLessThan(String value) {
            addCriterion("smtp_from_name <", value, "smtpFromName");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameLessThanOrEqualTo(String value) {
            addCriterion("smtp_from_name <=", value, "smtpFromName");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameLike(String value) {
            addCriterion("smtp_from_name like", value, "smtpFromName");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameNotLike(String value) {
            addCriterion("smtp_from_name not like", value, "smtpFromName");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameIn(List<String> values) {
            addCriterion("smtp_from_name in", values, "smtpFromName");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameNotIn(List<String> values) {
            addCriterion("smtp_from_name not in", values, "smtpFromName");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameBetween(String value1, String value2) {
            addCriterion("smtp_from_name between", value1, value2, "smtpFromName");
            return (Criteria) this;
        }

        public Criteria andSmtpFromNameNotBetween(String value1, String value2) {
            addCriterion("smtp_from_name not between", value1, value2, "smtpFromName");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlIsNull() {
            addCriterion("attachment_url is null");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlIsNotNull() {
            addCriterion("attachment_url is not null");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlEqualTo(String value) {
            addCriterion("attachment_url =", value, "attachmentUrl");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlNotEqualTo(String value) {
            addCriterion("attachment_url <>", value, "attachmentUrl");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlGreaterThan(String value) {
            addCriterion("attachment_url >", value, "attachmentUrl");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlGreaterThanOrEqualTo(String value) {
            addCriterion("attachment_url >=", value, "attachmentUrl");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlLessThan(String value) {
            addCriterion("attachment_url <", value, "attachmentUrl");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlLessThanOrEqualTo(String value) {
            addCriterion("attachment_url <=", value, "attachmentUrl");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlLike(String value) {
            addCriterion("attachment_url like", value, "attachmentUrl");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlNotLike(String value) {
            addCriterion("attachment_url not like", value, "attachmentUrl");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlIn(List<String> values) {
            addCriterion("attachment_url in", values, "attachmentUrl");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlNotIn(List<String> values) {
            addCriterion("attachment_url not in", values, "attachmentUrl");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlBetween(String value1, String value2) {
            addCriterion("attachment_url between", value1, value2, "attachmentUrl");
            return (Criteria) this;
        }

        public Criteria andAttachmentUrlNotBetween(String value1, String value2) {
            addCriterion("attachment_url not between", value1, value2, "attachmentUrl");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirIsNull() {
            addCriterion("attachment_dir is null");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirIsNotNull() {
            addCriterion("attachment_dir is not null");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirEqualTo(String value) {
            addCriterion("attachment_dir =", value, "attachmentDir");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirNotEqualTo(String value) {
            addCriterion("attachment_dir <>", value, "attachmentDir");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirGreaterThan(String value) {
            addCriterion("attachment_dir >", value, "attachmentDir");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirGreaterThanOrEqualTo(String value) {
            addCriterion("attachment_dir >=", value, "attachmentDir");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirLessThan(String value) {
            addCriterion("attachment_dir <", value, "attachmentDir");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirLessThanOrEqualTo(String value) {
            addCriterion("attachment_dir <=", value, "attachmentDir");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirLike(String value) {
            addCriterion("attachment_dir like", value, "attachmentDir");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirNotLike(String value) {
            addCriterion("attachment_dir not like", value, "attachmentDir");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirIn(List<String> values) {
            addCriterion("attachment_dir in", values, "attachmentDir");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirNotIn(List<String> values) {
            addCriterion("attachment_dir not in", values, "attachmentDir");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirBetween(String value1, String value2) {
            addCriterion("attachment_dir between", value1, value2, "attachmentDir");
            return (Criteria) this;
        }

        public Criteria andAttachmentDirNotBetween(String value1, String value2) {
            addCriterion("attachment_dir not between", value1, value2, "attachmentDir");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeIsNull() {
            addCriterion("attachment_type is null");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeIsNotNull() {
            addCriterion("attachment_type is not null");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeEqualTo(String value) {
            addCriterion("attachment_type =", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeNotEqualTo(String value) {
            addCriterion("attachment_type <>", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeGreaterThan(String value) {
            addCriterion("attachment_type >", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("attachment_type >=", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeLessThan(String value) {
            addCriterion("attachment_type <", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeLessThanOrEqualTo(String value) {
            addCriterion("attachment_type <=", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeLike(String value) {
            addCriterion("attachment_type like", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeNotLike(String value) {
            addCriterion("attachment_type not like", value, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeIn(List<String> values) {
            addCriterion("attachment_type in", values, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeNotIn(List<String> values) {
            addCriterion("attachment_type not in", values, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeBetween(String value1, String value2) {
            addCriterion("attachment_type between", value1, value2, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentTypeNotBetween(String value1, String value2) {
            addCriterion("attachment_type not between", value1, value2, "attachmentType");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadIsNull() {
            addCriterion("attachment_maxupload is null");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadIsNotNull() {
            addCriterion("attachment_maxupload is not null");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadEqualTo(String value) {
            addCriterion("attachment_maxupload =", value, "attachmentMaxupload");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadNotEqualTo(String value) {
            addCriterion("attachment_maxupload <>", value, "attachmentMaxupload");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadGreaterThan(String value) {
            addCriterion("attachment_maxupload >", value, "attachmentMaxupload");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadGreaterThanOrEqualTo(String value) {
            addCriterion("attachment_maxupload >=", value, "attachmentMaxupload");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadLessThan(String value) {
            addCriterion("attachment_maxupload <", value, "attachmentMaxupload");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadLessThanOrEqualTo(String value) {
            addCriterion("attachment_maxupload <=", value, "attachmentMaxupload");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadLike(String value) {
            addCriterion("attachment_maxupload like", value, "attachmentMaxupload");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadNotLike(String value) {
            addCriterion("attachment_maxupload not like", value, "attachmentMaxupload");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadIn(List<String> values) {
            addCriterion("attachment_maxupload in", values, "attachmentMaxupload");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadNotIn(List<String> values) {
            addCriterion("attachment_maxupload not in", values, "attachmentMaxupload");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadBetween(String value1, String value2) {
            addCriterion("attachment_maxupload between", value1, value2, "attachmentMaxupload");
            return (Criteria) this;
        }

        public Criteria andAttachmentMaxuploadNotBetween(String value1, String value2) {
            addCriterion("attachment_maxupload not between", value1, value2, "attachmentMaxupload");
            return (Criteria) this;
        }

        public Criteria andCdnDomainIsNull() {
            addCriterion("cdn_domain is null");
            return (Criteria) this;
        }

        public Criteria andCdnDomainIsNotNull() {
            addCriterion("cdn_domain is not null");
            return (Criteria) this;
        }

        public Criteria andCdnDomainEqualTo(String value) {
            addCriterion("cdn_domain =", value, "cdnDomain");
            return (Criteria) this;
        }

        public Criteria andCdnDomainNotEqualTo(String value) {
            addCriterion("cdn_domain <>", value, "cdnDomain");
            return (Criteria) this;
        }

        public Criteria andCdnDomainGreaterThan(String value) {
            addCriterion("cdn_domain >", value, "cdnDomain");
            return (Criteria) this;
        }

        public Criteria andCdnDomainGreaterThanOrEqualTo(String value) {
            addCriterion("cdn_domain >=", value, "cdnDomain");
            return (Criteria) this;
        }

        public Criteria andCdnDomainLessThan(String value) {
            addCriterion("cdn_domain <", value, "cdnDomain");
            return (Criteria) this;
        }

        public Criteria andCdnDomainLessThanOrEqualTo(String value) {
            addCriterion("cdn_domain <=", value, "cdnDomain");
            return (Criteria) this;
        }

        public Criteria andCdnDomainLike(String value) {
            addCriterion("cdn_domain like", value, "cdnDomain");
            return (Criteria) this;
        }

        public Criteria andCdnDomainNotLike(String value) {
            addCriterion("cdn_domain not like", value, "cdnDomain");
            return (Criteria) this;
        }

        public Criteria andCdnDomainIn(List<String> values) {
            addCriterion("cdn_domain in", values, "cdnDomain");
            return (Criteria) this;
        }

        public Criteria andCdnDomainNotIn(List<String> values) {
            addCriterion("cdn_domain not in", values, "cdnDomain");
            return (Criteria) this;
        }

        public Criteria andCdnDomainBetween(String value1, String value2) {
            addCriterion("cdn_domain between", value1, value2, "cdnDomain");
            return (Criteria) this;
        }

        public Criteria andCdnDomainNotBetween(String value1, String value2) {
            addCriterion("cdn_domain not between", value1, value2, "cdnDomain");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberIsNull() {
            addCriterion("service_phone_number is null");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberIsNotNull() {
            addCriterion("service_phone_number is not null");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberEqualTo(String value) {
            addCriterion("service_phone_number =", value, "servicePhoneNumber");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberNotEqualTo(String value) {
            addCriterion("service_phone_number <>", value, "servicePhoneNumber");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberGreaterThan(String value) {
            addCriterion("service_phone_number >", value, "servicePhoneNumber");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberGreaterThanOrEqualTo(String value) {
            addCriterion("service_phone_number >=", value, "servicePhoneNumber");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberLessThan(String value) {
            addCriterion("service_phone_number <", value, "servicePhoneNumber");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberLessThanOrEqualTo(String value) {
            addCriterion("service_phone_number <=", value, "servicePhoneNumber");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberLike(String value) {
            addCriterion("service_phone_number like", value, "servicePhoneNumber");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberNotLike(String value) {
            addCriterion("service_phone_number not like", value, "servicePhoneNumber");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberIn(List<String> values) {
            addCriterion("service_phone_number in", values, "servicePhoneNumber");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberNotIn(List<String> values) {
            addCriterion("service_phone_number not in", values, "servicePhoneNumber");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberBetween(String value1, String value2) {
            addCriterion("service_phone_number between", value1, value2, "servicePhoneNumber");
            return (Criteria) this;
        }

        public Criteria andServicePhoneNumberNotBetween(String value1, String value2) {
            addCriterion("service_phone_number not between", value1, value2, "servicePhoneNumber");
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