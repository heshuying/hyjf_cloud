/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.HjhPlanRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.trade.HjhPlanCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.UserHjhInvistDetailCustomize;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version HjhPlanService, v0.1 2018/6/13 17:23
 */
public interface HjhPlanService {
    /**
     * 获取现金贷资产方信息配置
     *
     * @param instCode
     * @return
     */
    List<HjhInstConfig> selectHjhInstConfigByInstCode(String instCode);

    /**
     * 获取标签
     *
     * @param borrowStyle
     * @return
     */
    List<HjhLabel> seleHjhLabelByBorrowStyle(String borrowStyle);

    /**
     * @Description 根据计划编号查询计划
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 14:08
     */
    HjhPlan getHjhPlanByNid(String planNid);

    /**
     * 插入计划明细表
     * @param planAccede
     * @param userAccount
     * @return
     */
    int insertHJHPlanAccede(HjhAccedeVO planAccede, Account userAccount);

    /**
     * 获取汇计划投资详情
     * @param params
     * @return
     */
    UserHjhInvistDetailCustomize selectUserHjhInvistDetail(Map<String,Object> params);

    /**
     * 获取app首页hjh数据
     * @date 2018/7/9 11:42
     */
    List<HjhPlanCustomize> selectAppHomeHjhPlan(HjhPlanRequest request);

    /**
     * @Author walter.limeng
     * @Description   根据plannid获取计划标的
     * @Date 11:26 2018/7/17
     * @Param planNid
     * @return
     */
    HjhPlanVO getHjhPlan(String planNid);

    /**
     * @Author walter.limeng
     * @Description  取得计划加入记录
     * @Date 11:48 2018/7/17
     * @Param orderId
     * @return
     */
    HjhAccedeVO getHjhAccede(String orderId);

    /**
     * 查询计划标的组成count
     * @author zhangyk
     * @date 2018/7/23 10:42
     */
    int getPlanBorrowListCount(Map<String,Object> params);

    /**
     * 查询计划标的组成list
     * @author zhangyk
     * @date 2018/7/23 10:42
     */
    List<BorrowVO> getPlanBorrowList(Map<String,Object> params);

    /**
     * 加入总数和接入金额总计
     * @author zhangyk
     * @date 2018/7/24 19:04
     */
    Map<String,Object> getPlanAccecdeTotal(Map<String,Object> params);

    /**
     * 加入记录list
     * @author zhangyk
     * @date 2018/7/24 19:04
     */
    List<HjhAccedeCustomizeVO> getPlanAccecdeList(Map<String,Object> params);
}
