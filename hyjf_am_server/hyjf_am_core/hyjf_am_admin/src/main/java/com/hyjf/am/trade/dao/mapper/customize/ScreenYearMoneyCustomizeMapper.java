/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.trade.RepaymentPlanVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lisheng
 * @version ScreenYearMoneyCustomizeMapper
 */
public interface ScreenYearMoneyCustomizeMapper {

    /**
     * 批量插入本月待回款用户数据
     * @param repaymentPlanVOS
     * @return
     */
    Integer addRepayUserList(@Param("repaymentPlanVOS") List<RepaymentPlanVO> repaymentPlanVOS);

    }
