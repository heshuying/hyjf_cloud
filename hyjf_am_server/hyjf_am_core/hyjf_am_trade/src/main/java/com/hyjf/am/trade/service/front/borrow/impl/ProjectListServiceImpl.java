/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.trade.service.front.borrow.ProjectListService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.am.vo.api.ApiProjectListCustomize;
import com.hyjf.am.vo.trade.AppProjectListCustomizeVO;
import com.hyjf.am.vo.trade.CreditListVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WechatHomeProjectListVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Web端项目列表Service实现类
 *
 * @author liuyang
 * @version ProjectListServiceImpl, v0.1 2018/6/13 11:38
 */
@Service
public class ProjectListServiceImpl extends BaseServiceImpl implements ProjectListService {


    /**
     * 获取标的列表
     * @param request
     * @return
     */
    @Override
    public List<WebProjectListCustomize> searchProjectList(@Valid ProjectListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 项目类型
        String projectType = request.getProjectType();
        // 项目子类型
        String borrowClass = request.getBorrowClass();
        // 分页起始
        Integer limitStart = request.getLimitStart();
        // 分页结束
        Integer limitEnd = request.getLimitEnd();
        params.put("projectType", projectType);
        params.put("borrowClass", borrowClass);
        params.put("status", request.getStatus());
        params.put("limitStart", limitStart);
        params.put("limitEnd", limitEnd);
        params.put("publishInstCode",StringUtils.isBlank(request.getPublishInstCode()) ? "" : request.getPublishInstCode());
        params.put("wjtInstCode", request.getWjtInstCode());
        return webProjectListCustomizeMapper.searchProjectList(params);
    }

    /**
     * 获取标的列表件数
     *
     * @param request
     * @return
     */
    @Override
    public int countProjectList(@Valid ProjectListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 项目类型
        String projectType = request.getProjectType();
        // 项目子类型
        String borrowClass = request.getBorrowClass();
        // 分页起始
        Integer limitStart = request.getLimitStart();
        // 分页结束
        Integer limitEnd = request.getLimitEnd();
        params.put("projectType", projectType);
        params.put("borrowClass", borrowClass);
        params.put("status", request.getStatus());
        params.put("limitStart",limitStart);
        params.put("limitEnd", limitEnd);
        params.put("publishInstCode",StringUtils.isBlank(request.getPublishInstCode()) ? "" : request.getPublishInstCode());
        return webProjectListCustomizeMapper.countProjectList(params);
    }


    /**
     * Web端获取标的详情
     * @author zhangyk
     * @date 2018/6/23 13:56
     */
    @Override
    public ProjectCustomeDetailVO getProjectDetail(@Valid Map map) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowNid",map.get("borrowNid"));
        params.put("isPreview",map.get("isPreview") == null ? "" : map.get("isPreview"));
        return  webProjectListCustomizeMapper.getProjectDetail(map);
    }


    /**
     * @desc  债转列表count
     * @author zhangyk
     * @date 2018/6/19 15:58
     */
    @Override
    public int countCreditList(@Valid CreditListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 获取项目期限
        params.put("borrowPeriodMin", request.getBorrowPeriodMin());
        params.put("borrowPeriodMax", request.getBorrowPeriodMax());
        // 获取项目收益
        params.put("borrowAprMin", request.getBorrowAprMin());
        params.put("borrowAprMax", request.getBorrowAprMax());
        // 获取折价比例排序
        params.put("discountSort", request.getDiscountSort());
        // 获取期限排序
        params.put("termSort", request.getTermSort());
        // 获取金额排序
        params.put("capitalSort",request.getCapitalSort());
        // 进度排序
        params.put("inProgressSort",request.getInProgressSort());
        params.put("creditStatus", "0");
        int count = webProjectListCustomizeMapper.countCreditList(params);
        return count;
    }


    /**
     * @desc  获取债转列表数据list
     * @author zhangyk
     * @date 2018/6/19 16:03
     */
    @Override
    public List<CreditListVO> searchCreditList(@Valid CreditListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 获取项目期限
        params.put("borrowPeriodMin", request.getBorrowPeriodMin());
        params.put("borrowPeriodMax", request.getBorrowPeriodMax());
        // 获取项目收益
        params.put("borrowAprMin", request.getBorrowAprMin());
        params.put("borrowAprMax", request.getBorrowAprMax());
        // 获取折价比例排序
        params.put("discountSort", request.getDiscountSort());
        // 获取期限排序
        params.put("termSort", request.getTermSort());
        // 获取金额排序
        params.put("capitalSort",request.getCapitalSort());
        // 进度排序
        params.put("inProgressSort",request.getInProgressSort());
        params.put("limitStart",request.getLimitStart());
        params.put("limitEnd", request.getLimitEnd());
        params.put("creditStatus", "0");
        List<CreditListVO> list = webProjectListCustomizeMapper.searchCreditList(params);
        return list;
    }


    /**
     * Web端获取计划专区上部统计数据
     * @author zhangyk
     * @date 2018/6/21 15:42
     */
    @Override
    public Map<String, Object> searchPlanData() {
        return webProjectListCustomizeMapper.searchPlanData();
    }

    /**
     * Web端获取计划专区计划列表count
     * @author zhangyk
     * @date 2018/6/21 15:51
     */
    @Override
    public int countWebPlanList(ProjectListRequest request) {
        Map<String,Object> params = new HashMap<>();
        params.put("limitStart",request.getLimitStart());
        params.put("limitEnd",request.getLimitEnd());
        if (StringUtils.isNotBlank(request.getIsHome())){
            params.put("isHome",request.getIsHome());
        }
        return webProjectListCustomizeMapper.countWebPlanList(params);
    }


    /**
     * Web端获取计划专区计划列表list
     * @author zhangyk
     * @date 2018/6/21 15:53
     */
    @Override
    public List<HjhPlanCustomize> searchWebPlanList(ProjectListRequest request) {
        Map<String,Object> params = new HashMap<>();
        params.put("limitStart",request.getLimitStart());
        params.put("limitEnd",request.getLimitEnd());
        if (StringUtils.isNotBlank(request.getIsHome())){
            params.put("isHome",request.getIsHome());
        }
        return webProjectListCustomizeMapper.searchWebPlanList(params);
    }

    /**
     * web端获取计划基本详情
     * @author zhangyk
     * @date 2018/7/14 18:08
     */
    @Override
    public PlanDetailCustomize getPlanDetail(String planNid){
        return webProjectListCustomizeMapper.getPlanDetail(planNid);
    }


    // ----------------------------------------web end ----------------------------------------------------
    // ----------------------------------------app start --------------------------------------------------

    /**
     * app端获取散标出借count
     * @author zhangyk
     * @date 2018/6/20 16:13
     */
    @Override
    public int countAppProjectList(@Valid ProjectListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 项目类型
        String projectType = request.getProjectType();
        // 项目子类型
        String borrowClass = request.getBorrowClass();
        // 分页起始
        Integer limitStart = request.getLimitStart();
        // 分页结束
        Integer limitEnd = request.getLimitEnd();
        params.put("projectType", projectType);
        params.put("borrowClass", borrowClass);
        params.put("host",request.getHost());
        params.put("type",request.getType());
        params.put("platform",request.getPlatform());
        params.put("status",request.getStatus());
        return webProjectListCustomizeMapper.countAppProject(params);
    }

    /**
     * app端获取散标出借数据list
     * @author zhangyk
     * @date 2018/6/20 16:11
     */
    @Override
    public List<AppProjectListCustomize> searchAppProjectList(@Valid ProjectListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 项目类型
        String projectType = request.getProjectType();
        // 项目子类型
        String borrowClass = request.getBorrowClass();
        // 分页起始
        Integer limitStart = request.getLimitStart();
        // 分页结束
        Integer limitEnd = request.getLimitEnd();
        params.put("projectType", projectType);
        params.put("borrowClass", borrowClass);
        params.put("limitStart",limitStart);
        params.put("limitEnd", limitEnd);
        params.put("host",request.getHost());
        params.put("type",request.getType());
        params.put("platform",request.getPlatform());
        params.put("status",request.getStatus());
        return webProjectListCustomizeMapper.searchAppProjectList(params);
    }

    @Override
    public int countAppCreditList(@Valid ProjectListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 项目类型
        String projectType = request.getProjectType();
        // 项目子类型
        String borrowClass = request.getBorrowClass();
        params.put("projectType", projectType);
        params.put("borrowClass", borrowClass);
        params.put("creditStatus",request.getCreditStatus());
        return webProjectListCustomizeMapper.countAppCredit(params);
    }

    @Override
    public List<WebProjectListCustomize> searchAppCreditList(@Valid ProjectListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 项目类型
        String projectType = request.getProjectType();
        // 项目子类型
        String borrowClass = request.getBorrowClass();
        // 分页起始
        Integer limitStart = request.getLimitStart();
        // 分页结束
        Integer limitEnd = request.getLimitEnd();
        params.put("projectType", projectType);
        params.put("borrowClass", borrowClass);
        params.put("limitStart",limitStart);
        params.put("limitEnd", limitEnd);
        params.put("creditStatus",request.getCreditStatus());
        return webProjectListCustomizeMapper.searchAppCreditList(params);
    }

    @Override
    public int countAppPlanList(@Valid ProjectListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 项目类型
        String projectType = request.getProjectType();
        // 项目子类型
        String borrowClass = request.getBorrowClass();
        // 分页起始
        Integer limitStart = request.getLimitStart();
        // 分页结束
        Integer limitEnd = request.getLimitEnd();
        params.put("isHome",request.getIsHome());
        params.put("projectType", projectType);
        params.put("borrowClass", borrowClass);
        return webProjectListCustomizeMapper.countAppPlanList(params);
    }

    @Override
    public List<HjhPlanVO> searchAppPlanList(@Valid ProjectListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 项目类型
        String projectType = request.getProjectType();
        // 项目子类型
        String borrowClass = request.getBorrowClass();
        // 分页起始
        Integer limitStart = request.getLimitStart();
        // 分页结束
        Integer limitEnd = request.getLimitEnd();
        params.put("isHome",request.getIsHome());
        params.put("projectType", projectType);
        params.put("borrowClass", borrowClass);
        params.put("limitStart",limitStart);
        params.put("limitEnd", limitEnd);
        return webProjectListCustomizeMapper.searchAppPlanList(params);
    }

    /**
     * app端散标出借记录数
     * @param params
     * @return
     */
    @Override
    public int countProjectInvestRecordTotal(Map<String, Object> params) {
        int hztInvestTotal = appProjectListCustomizeMapper.countProjectInvestRecordTotal(params);
        return hztInvestTotal;
    }

    /**
     * 散标出借记录
     * @param params
     * @return
     */
    @Override
    public List<AppProjectInvestListCustomize> selectProjectInvestList(Map<String, Object> params) {
        List<AppProjectInvestListCustomize> list = appProjectListCustomizeMapper.selectProjectInvestList(params);
        return list;
    }

    /**
     * app端债转承接记录数
     * @param params
     * @return
     */
    @Override
    public int countTenderCreditInvestRecordTotal(Map<String, Object> params) {
        return appTenderCreditCustomizeMapper.countTenderCreditInvestRecordTotal(params);
    }

    /**
     * app端债转承接记录列表
     * @param params
     * @return
     */
    @Override
    public List<AppTenderCreditInvestListCustomize> searchTenderCreditInvestList(Map<String, Object> params) {
        return appTenderCreditCustomizeMapper.searchTenderCreditInvestList(params);
    }


    @Override
    public List<BorrowCredit> selectBorrowCreditByNid(String creditNid) {
        // 获取borrow_credit数据
        BorrowCreditExample borrowCreditExample = new BorrowCreditExample();
        BorrowCreditExample.Criteria borrowCreditCra = borrowCreditExample.createCriteria();
        borrowCreditCra.andCreditNidEqualTo(Integer.parseInt(creditNid));
        // 获取还款数据
        List<BorrowCredit> borrowCreditList = this.borrowCreditMapper.selectByExample(borrowCreditExample);
        return borrowCreditList;
    }
    /**
     * 根据订单号查询产品加息信息
     * @auth sunpeikai
     * @param orderId 订单id
     * @return
     */
    @Override
    public IncreaseInterestInvest getIncreaseInterestInvestByOrdId(String orderId) {
        IncreaseInterestInvestExample example = new IncreaseInterestInvestExample();
        IncreaseInterestInvestExample.Criteria cra = example.createCriteria();
        cra.andOrderIdEqualTo(orderId);
        List<IncreaseInterestInvest> list = increaseInterestInvestMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    /**
     * 查询产品加息信息
     * @auth sunpeikai
     * @param tenderNid 对应tender表里的nid
     * @return
     */
    @Override
    public IncreaseInterestInvest getIncreaseInterestInvestByTenderNid(String tenderNid) {
        IncreaseInterestInvestExample example = new IncreaseInterestInvestExample();
        IncreaseInterestInvestExample.Criteria cra = example.createCriteria();
        cra.andTenderNidEqualTo(tenderNid);
        List<IncreaseInterestInvest> list = increaseInterestInvestMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
// ----------------------------------------app end ----------------------------------------------------


    //  -----------------------------------------wechat start ----------------------------------------------


    /**
     * 查询微信端首页产品列表
     * @author zhangyk
     * @date 2018/7/24 13:45
     */
    @Override
    public List<WechatHomeProjectListVO> searchWechatProjectList(Map<String, Object> params) {
        return  webProjectListCustomizeMapper.searchWechatProjectList(params);
    }

    /**
     * 微信端加载两条计划稍后开启
     * @author zhangyk
     * @date 2018/7/24 14:30
     */
    @Override
    public List<WechatHomeProjectListVO> selectHomeHjhOpenLaterList() {
        return webProjectListCustomizeMapper.selectHomeHjhOpenLaterList();
    }

    /**
     * 首页无可投散标加载两条还款中和复审中记录
     * @author zhangyk
     * @date 2018/7/24 14:33
     */
    @Override
    public List<WechatHomeProjectListVO> selectHomeRepaymentsProjectList() {
        return webProjectListCustomizeMapper.selectHomeRepaymentsProjectList();
    }


    //  -----------------------------------------wechat end   -----------------------------------------------


    /*--------------------------------------------  api start  -------------------------------------------*/

    /**
     * api： 查询标的列表
     * @author zhangyk
     * @date 2018/8/27 14:14
     */
    @Override
    public List<ApiProjectListCustomize> getApiBorrowList(Map<String,Object> params) {
        return webProjectListCustomizeMapper.getApiBorrowList(params);
    }
    /*--------------------------------------------  api end  -------------------------------------------*/

    /**
     *首页汇计划推广计划列表 - 首页显示 ②	若没有可投计划，则显示锁定期限短的
     * @Author yangchangwei 2018/10/16
     * @param map
     * @return
     */
    @Override
    public List<HjhPlanCustomizeVO> getIndexHjhExtensionPlanListByLockTime(Map map) {
        return this.appProjectListCustomizeMapper.getIndexHjhExtensionPlanListByLockTime(map);
    }

    /**
     * 首页汇计划推广计划列表 - 首页显示
     * @Author yangchangwei 2018/10/16
     * @param map
     * @return
     */
    @Override
    public List<HjhPlanCustomizeVO> getIndexHjhExtensionPlanList(Map map) {
        return this.appProjectListCustomizeMapper.getIndexHjhExtensionPlanList(map);
    }

    /**
     * 首页汇计划推广计划列表 - 首页显示
     * @Author yangchangwei 2018/10/16
     * @param map
     * @return
     */
    @Override
    public List<AppProjectListCustomizeVO> getHomeProjectList(Map map) {
        return this.appProjectListCustomizeMapper.selectHomeProjectList(map);
    }



    /**
     * app首页获取有效公告
     * @author cwyang 2018-10-18
     * @return
     */
    @Override
    public List<AppPushManageVO> getAnnouncements() {
        AppPushManageExample example = new AppPushManageExample();
        AppPushManageExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(1);
        criteria.andTimeStartLessThanOrEqualTo(new Date());
        criteria.andTimeEndGreaterThanOrEqualTo(new Date());
        example.setOrderByClause(" `order_id` asc ,create_time desc");
        List<AppPushManage> pushManageList = this.appPushManageMapper.selectByExample(example);
        if(pushManageList != null && pushManageList.size() > 0){
            List<AppPushManageVO> appPushManageVOS = CommonUtils.convertBeanList(pushManageList, AppPushManageVO.class);
            return appPushManageVOS;
        }
        return null;
    }

    /**
     * app首页通过ID获取有效公告
     * @author cwyang
     * @param id
     * @return
     */
    @Override
    public AppPushManageVO getAnnouncementsByID(String id) {
        AppPushManage appPushManage = this.appPushManageMapper.selectByPrimaryKey(Integer.valueOf(id));
        if(appPushManage != null){
            AppPushManageVO appPushManageVO = CommonUtils.convertBean(appPushManage, AppPushManageVO.class);
            return appPushManageVO;
        }
        return null;
    }

    /**
     * app首页获取还款中的项目
     * add by cwyang
     * @param map
     * @return
     */
    @Override
    public List<AppProjectListCustomizeVO> getHomeRepayProjecList(Map map) {

        return appProjectListCustomizeMapper.selectHomeRepayProjectList(map);
    }

}
