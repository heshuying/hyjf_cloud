package com.hyjf.am.trade.service;

import com.hyjf.am.vo.trade.RepaymentPlanVO;

import java.util.Date;
import java.util.List;

/**
 * @author lisheng
 * @version BorrowRepayScreenDataService, v0.1 2019/3/26 9:02
 */

public interface BorrowRepayScreenDataService {
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

}
