package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.PlanListCustomizeRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;

import java.util.List;
import java.util.Map;

/**
 * @Description 计划类
 * @Author sunss
 * @Date 2018/6/22 15:41
 */
public interface HjhPlanCustomizeMapper {

    /**
     * 更新相应的汇计划专属标的加入的用户的账户信息
     *
     * @param investAccount
     * @return
     */
    int updateOfPlanJoin(Account investAccount);

    /**
     * 加入计划后更新计划总信息
     * @param planUpdate
     * @return
     */
    int updateByDebtPlanId(Map<String,Object> planUpdate);

    /**
     * 更新还款后复投的开放额度
     * @param hjhPlan
     * @return
     */
    int updateRepayPlanAccount(HjhPlan hjhPlan);

    /**
     * 获取汇计划出借详情
     * @param params
     * @return
     */
    UserHjhInvistDetailCustomize selectUserHjhInvistDetail(Map<String,Object> params);

    /**
     * 更新汇计划还款信息
     * @param repayParam
     * @return
     */
    int updateHjhRepayForHjhRepay(HjhRepay repayParam);

    /**
     * 更新汇计划出借明细金额
     * @param hjhAccede
     * @return
     */
    int updateHjhAccedeForHjhProcess(HjhAccede hjhAccede);
    
    /**
     * 获取汇计划sum
     * @param request
     * @return
     */
    HjhPlanSumVO getCalcSumByParam(PlanListCustomizeRequest request);

    /**
     * 获取app首页汇计划列表
     * @param params
     * @return
     */
    List<HjhPlanCustomize> getHjhPlanAppList(Map<String,Object> params);

    /**
     * @Author walter.limeng
     * @Description  更新对象
     * @Date 11:28 2018/7/12
     * @Param 
     * @return
     */
    void updatePlanAccount(HjhPlan hjhPlan);

    /**
     *  查询计划标的组成count
     * @date 2018/7/23 10:43
     */
    int getPlanBorrowListCount(Map<String,Object> params);

    /**
     *  查询计划标的组成list
     * @date 2018/7/23 10:43
     */
    List<BorrowAndInfoVO> getPlanBorrowList(Map<String,Object> params);

    /**
     * 计划加入总数 和加入金额总计
     * @author zhangyk
     * @date 2018/7/24 19:05
     */
    Map<String,Object> getPlanAccedeTotal(Map<String,Object> params);


    /**
     * 加入记录list
     * @author zhangyk
     * @date 2018/7/24 19:05
     */
    List<HjhAccedeCustomizeVO> getPlanAccedeList(Map<String,Object> params);

    /**
     * 统计相应的计划加入记录总数
     * @param params
     * @return
     */
    int countPlanBorrowRecordTotal(Map<String,Object> params);

    /**
     * 计划标的组成
     * @param params
     * @return
     */
    List<DebtPlanBorrowCustomize> selectPlanBorrowList(Map<String,Object> params);

    /**
     * 统计相应的计划总数
     * @param params
     * @return
     */
    Long selectPlanAccedeSum(Map<String,Object> params);

    /**
     * 查询相应的计划的加入明细
     * @param params
     * @return
     */
    List<DebtPlanAccedeCustomize> selectPlanAccedeList(Map<String,Object> params);

    /**
     * 查询用户的汇计划加入记录
     * @date 2018/8/1 14:03
     */
    List<UserHjhInvistListCustomize> getUserHjhInvestList(Map<String,Object> params);

    /**
     * 查询用户的汇计划加入记录COUNT
     * @author zhangyk
     * @date 2018/11/22 16:09
     */
    int getUserHjhInvestCount(Map<String,Object> params);

    /**
     * 汇计划数据统计查询
     * @return
     */
    List<Map<String,Object>> searchPlanStatisticData();


    /**
     * 更新相应的汇计划专属标的加入的用户的账户信息
     *
     * @param investAccount
     * @return
     */
    int updateOfHjhPlanJoin(Account investAccount);

    /**
     * 统计最后三天的服务记录
     * app和危险的统计计划加入数量
     * @param params
     * @return
     */
    int countPlanAccedeRecord(Map<String,Object> params);

    /**
     *获取加入计划的最后三天日期
     * add by nxl
     * @param planNid
     * @return
     */
    List<HjhAccedeCustomizeVO> selectLastThreeDate(String  planNid);

}
