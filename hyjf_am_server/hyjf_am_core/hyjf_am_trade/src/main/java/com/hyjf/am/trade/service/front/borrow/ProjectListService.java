/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestInvest;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.am.vo.api.ApiProjectListCustomize;
import com.hyjf.am.vo.trade.AppProjectListCustomizeVO;
import com.hyjf.am.vo.trade.CreditListVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WechatHomeProjectListVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * Web端项目列表Service
 * @author liuyang
 * @version ProjectListService, v0.1 2018/6/13 11:37
 */
public interface ProjectListService {


    /**
     * Web端获取项目列表
     * @param request
     * @return
     */
    List<WebProjectListCustomize> searchProjectList(@Valid ProjectListRequest request);

    /**
     * Web端获取项目列表件数
     * @param request
     * @return
     */
    int countProjectList(@Valid ProjectListRequest request);

    /**
     * Web端获取标的详情
     * @author zhangyk
     * @date 2018/6/23 13:55
     */
    ProjectCustomeDetailVO getProjectDetail(@Valid Map map);

    /**
     * Web端获取债转列表count
     * @param request
     * @return
     */
    int countCreditList(@Valid CreditListRequest request);

    /**
     * Web端获取债转列表list
     * @author zhangyk
     * @date 2018/6/19 16:00
     */
    List<CreditListVO> searchCreditList(@Valid CreditListRequest request);

    /**
     * Web端获取计划专区上部统计数据
     * @author zhangyk
     * @date 2018/6/21 15:42
     */
    Map<String,Object>  searchPlanData();

    /**
     * Web端获取计划专区计划列表count
     * @author zhangyk
     * @date 2018/6/21 15:51
     */
    int countWebPlanList(ProjectListRequest request);

    /**
     * Web端获取计划专区计划列表list
     * @author zhangyk
     * @date 2018/6/21 15:51
     */
    List<HjhPlanCustomize> searchWebPlanList(ProjectListRequest request);


    /**
     * web端获取计划基本详情
     * @author zhangyk
     * @date 2018/7/14 18:08
     */
    PlanDetailCustomize getPlanDetail(String planNid);



    // --------------------------web end --------------------------------------------------
    //---------------------------app start ------------------------------------------------
    /**
     * app端获取散标出借count
     * @author zhangyk
     * @date 2018/6/20 16:11
     */
     int  countAppProjectList(@Valid ProjectListRequest request);

    /**
     * app端获取散标出借数据list
     * @author zhangyk
     * @date 2018/6/20 16:11
     */
    List<AppProjectListCustomize> searchAppProjectList(@Valid ProjectListRequest request);

    /**
     * app端获取散标出借count
     * @author zhangyk
     * @date 2018/6/20 16:11
     */
    int  countAppCreditList(@Valid ProjectListRequest request);

    /**
     * app端获取散标出借数据list
     * @author zhangyk
     * @date 2018/6/20 16:11
     */
    List<WebProjectListCustomize> searchAppCreditList(@Valid ProjectListRequest request);

    /**
     * app获取计划列表count
     * @author zhangyk
     * @date 2018/6/22 10:27
     */
    int countAppPlanList(@Valid ProjectListRequest request);

    /**
     * app获取计划列表list
     * @author zhangyk
     * @date 2018/6/22 10:27
     */
    List<HjhPlanVO>  searchAppPlanList(@Valid ProjectListRequest request);



    /*
     * 散表出借记录数
     */
    int countProjectInvestRecordTotal(Map<String,Object> params);

    /**
     * 获取散标出借记录
     * @param params
     * @return
     */
    List<AppProjectInvestListCustomize> selectProjectInvestList(Map<String,Object> params);

    /**
     * app端债转承接记录数
     * @param params
     * @return
     */
    int countTenderCreditInvestRecordTotal(Map<String,Object> params);

    /**
     * 债转承接记录列表
     * @param params
     * @return
     */
    List<AppTenderCreditInvestListCustomize> searchTenderCreditInvestList(Map<String,Object> params);

    List<BorrowCredit> selectBorrowCreditByNid(String transferId);
    /**
     * 根据订单号查询产品加息信息
     * @auth sunpeikai
     * @param orderId 订单id
     * @return
     */
    IncreaseInterestInvest getIncreaseInterestInvestByOrdId(String orderId);
    /**
     * 查询产品加息信息
     * @auth sunpeikai
     * @param tenderNid 对应tender表里的nid
     * @return
     */
    IncreaseInterestInvest getIncreaseInterestInvestByTenderNid(String tenderNid);
    // --------------------------app end --------------------------------------------------

    // -----------------------------wechat  start ------------------------------------------
    /**
     * 查询微信端首页产品列表
     * @author zhangyk
     * @date 2018/7/24 13:45
     */
    List<WechatHomeProjectListVO> searchWechatProjectList(Map<String,Object> params);

    /**
     * 微信端加载两条计划稍后开启
     * @author zhangyk
     * @date 2018/7/24 14:30
     */
    List<WechatHomeProjectListVO> selectHomeHjhOpenLaterList();

    /**
     * 首页无可投散标加载两条还款中和复审中记录
     * @author zhangyk
     * @date 2018/7/24 14:33
     */
    List<WechatHomeProjectListVO> selectHomeRepaymentsProjectList();


    // -----------------------------wechat end  ---------------------------------------------


    /*-------------------------------  api  start  -------------------------------------------*/
    /**
     * api： 查询标的列表
     * @author zhangyk
     * @date 2018/8/27 14:12
     */
    List<ApiProjectListCustomize> getApiBorrowList(Map<String,Object> params);

    /*-------------------------------  api  end    -------------------------------------------*/

    /**
     *首页汇计划推广计划列表 - 首页显示 ②	若没有可投计划，则显示锁定期限短的
     * @Author yangchangwei 2018/10/16
     * @param map
     * @return
     */
    List<HjhPlanCustomizeVO> getIndexHjhExtensionPlanListByLockTime(Map map);

    /**
     * 首页汇计划推广计划列表 - 首页显示
     * @Author yangchangwei 2018/10/16
     * @param map
     * @return
     */
    List<HjhPlanCustomizeVO> getIndexHjhExtensionPlanList(Map map);

    /**
     * 首页汇计划推广计划列表 - 首页显示
     * @Author yangchangwei 2018/10/16
     * @param map
     * @return
     */
    List<AppProjectListCustomizeVO> getHomeProjectList(Map map);

    /**
     * app首页获取有效公告
     * @author cwyang 2018-10-18
     * @return
     */
    List<AppPushManageVO> getAnnouncements();

    /**
     * 根据ID获取APP首页内容
     * @param id
     * @return
     */
    AppPushManageVO getAnnouncementsByID(String id);

    /**
     * 获取首页展示还款中的标的
     * @param map
     * @return
     */
    List<AppProjectListCustomizeVO> getHomeRepayProjecList(Map map);

}
