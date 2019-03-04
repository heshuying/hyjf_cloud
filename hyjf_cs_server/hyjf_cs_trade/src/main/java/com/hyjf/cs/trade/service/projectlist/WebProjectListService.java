package com.hyjf.cs.trade.service.projectlist;

import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.*;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.Map;

/**
 * web端项目列表Service
 *
 * @author liuyang
 * @version WebProjectListService, v0.1 2018/6/13 10:21
 */
public interface WebProjectListService extends BaseTradeService {

    /**
     * 获取Web端项目列表
     * @param request
     * @author liuyang
     * @return
     */
    public WebResult searchProjectList(ProjectListRequest request);

    /**
     * 获取Web端项目列表(新的)
     * @author zhangyk
     * @date 2018/10/9 15:44
     */
    public WebResult searchProjectListNew(ProjectListRequest request);


    /**
     * web端散标标的详情
     * @author zhangyk
     * @date 2018/6/22 16:40
     */
    public WebResult getBorrowDetail(Map map,String userId);

    /**
     * 测试方法  稍后删除
     */
    public WebResult getBorrowDetail4Test(Map map,String userId);

    /**
     * 获取新手标和散标详情：出借记录
     * @author zhangyk
     * @date 2018/8/9 16:58
     */
    public WebResult getBorrowInvest(BorrowInvestReqBean form, String userId);

    /**
     * 查询定时散标倒计时信息
     * @author zhangyk
     * @date 2018/9/3 14:48
     */
    public OntimeCheckBean getBorrowOntime(String borrowNid);

    /**
     * 获取散标专区债转列表
     * @author zhangyk
     * @date 2018/6/19 16:33
     */
    public WebResult searchCreditList(CreditListRequest request);

    /**
     * web端债转详情
     * @author zhangyk
     * @date 2018/6/26 9:56
     */
    public WebResult getCreditDetail(Map<String,Object> map,String userId);

    /**
     * web端债转详情:承接记录
     * @author zhangyk
     * @date 2018/6/26 9:56
     */
    public WebResult getCreditTenderList(WebCreditRequestBean requestBean);


    /**
     * 计划专区上部统计数据
     * @author zhangyk
     * @date 2018/6/19 16:33
     */
    public WebResult searchPlanData(ProjectListRequest request);


    /**
     * 计划专区计划列表数据
     * @author zhangyk
     * @date 2018/6/21 15:21
     */
    public WebResult searchPlanList(ProjectListRequest request);


    /**
     * 计划详情：主体信息
     * @author zhangyk
     * @date 2018/6/27 18:59
     */
    public WebResult getPlanDetail(Map<String,String> map,String userId);

    /**
     * 计划详情:标的组成
     * @author zhangyk
     * @date 2018/7/23 10:08
     */
    public WebResult getPlanBorrowList(WebPlanRequestBean request);


    /**
     * 计划详情:加入记录
     * @author zhangyk
     * @date 2018/7/24 18:51
     */
    public WebResult getPlanAccedeList(WebPlanRequestBean requestBean,  String userId);

    /**
     * 根据计划编号和用户id查询用户优惠券
     * @author zhangyk
     * @date 2018/8/16 11:08
     */
    public WebResult getProjectAvailableUserCoupon(WebPlanRequestBean requestBean,  Integer userId);

    /**
     * 查询计划标的组成的标的详情
     * @author zhangyk
     * @date 2018/8/17 9:49
     */
    public WebResult getPlanAccedeBorrowDetail(WebBorrowRequestBean requestBean , Integer userId);

    /**
     * 查询计划标的组成：承接记录
     * @author zhangyk
     * @date 2018/8/24 10:23
     */
    public WebResult getPlanBorrowUndertake(WebBorrowRequestBean requestBean );



}
