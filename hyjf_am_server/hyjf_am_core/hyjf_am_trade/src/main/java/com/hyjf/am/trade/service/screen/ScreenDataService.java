package com.hyjf.am.trade.service.screen;

import com.hyjf.am.resquest.trade.ScreenDataBean;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

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
     *
     * @param userId
     * @return
     */
    BigDecimal findYearMoney(Integer userId, String orderId, Integer productType, BigDecimal investMoney);

}
