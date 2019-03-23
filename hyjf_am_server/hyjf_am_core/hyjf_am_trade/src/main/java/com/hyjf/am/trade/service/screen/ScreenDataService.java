package com.hyjf.am.trade.service.screen;

import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.trade.dao.mapper.customize.ScreenYearMoneyCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.RepaymentPlan;
import com.hyjf.am.vo.trade.RepaymentPlanVO;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lisheng
 * @version ScreenDataService, v0.1 2019/3/18 14:16
 */

public interface ScreenDataService {
    /**
     * 添加一条运营部用户资金明细
     * @param screenDataBean
     * @return
     */
    Integer addUserOperateList(ScreenDataBean screenDataBean);

    /**
     * 查询用户的空闲
     * @param userId
     * @return
     */
    BigDecimal findUserFreeMoney(Integer userId);

    /**
     * 查询用户的年化投资金额
     * @param userId
     * @return
     */
    BigDecimal findYearMoney(Integer userId, String orderId, Integer productType, BigDecimal investMoney);


    /**
     * 批量插入本月待回款用户数据
     * @param repaymentPlanVOS
     * @return
     */
   Integer addRepayUserList(List<RepaymentPlanVO> repaymentPlanVOS);

    /**
     * 查询本月是否已生成数据
     * @return
     */
    Integer countRepayUserList(Date startTime, Date endTime);


    /**
     * 修改待回款金额
     * @param screenDataBean
     * @return
     */
    Integer updateRepayMoney(ScreenDataBean screenDataBean, Integer startTime, Integer endTime);

    /**
     * 查询用户这笔订单是否是本月应还的
     * @param screenDataBean
     * @param startTime
     * @param endTime
     * @return
     */
    RepaymentPlan findRepayUser(ScreenDataBean screenDataBean, Integer startTime, Integer endTime);

    /**
     * 插入一条待回款记录
     * @param repaymentPlan
     * @return
     */
    Integer insertRepayUser(RepaymentPlan repaymentPlan);

}
