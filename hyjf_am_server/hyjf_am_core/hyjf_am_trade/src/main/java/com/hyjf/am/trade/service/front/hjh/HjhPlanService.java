/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh;

import com.hyjf.am.resquest.trade.HjhPlanRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.customize.DebtPlanAccedeCustomize;
import com.hyjf.am.trade.dao.model.customize.DebtPlanBorrowCustomize;
import com.hyjf.am.trade.dao.model.customize.HjhPlanCustomize;
import com.hyjf.am.trade.dao.model.customize.UserHjhInvistDetailCustomize;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
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
     * @Description 根据计划编号查询计划
     * @Author liubin
     * @Version v0.1
     * @Date 2018/6/19 14:08
     */
    HjhPlan doGetHjhPlanByNid(String planNid);

    /**
     * 取得全部汇计划列表
     * @return
     */
    List<HjhPlan> selectHjhPlanList();

    /**
     * 插入计划明细表
     * @param planAccede
     * @param userAccount
     * @return
     */
    int insertHJHPlanAccede(HjhAccedeVO planAccede, Account userAccount);

    /**
     * 获取汇计划出借详情
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
    List<BorrowAndInfoVO> getPlanBorrowList(Map<String,Object> params);

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

    /**
     * 统计相应的计划加入记录总数
     * @param params
     * @return
     */
    int countPlanBorrowRecordTotal(Map<String,Object> params);

    /**
     * 查询相应的计划标的列表
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
     * 查询计划的加入记录
     * @param params
     * @return
     */
    List<DebtPlanAccedeCustomize> selectPlanAccedeList(Map<String,Object> params);

    /**
     * 更新显示的计划开启或者关闭
     * 1 开启计划 2 关闭计划
     * @param status
     * @return
     */
    int updateHjhPlanForJoinSwitch(int status);

    /**
     * 统计最后三天的服务记录 add by nxl
     * app和危险的统计计划加入数量
     * @param planNid
     * @return
     */
    Integer countPlanAccedeRecord(String planNid);

}
