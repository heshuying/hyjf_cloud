package com.hyjf.admin.service.screendata;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.RepayResponse;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.vo.trade.RepaymentPlanVO;

import java.util.List;

/**
 * @author lisheng
 * @version BorrowRepayDataService, v0.1 2019/3/20 14:47
 */

public interface BorrowRepayDataService {
    /**
     * 查询本月待回款的用户id
     * @param startTime
     * @param endTime
     * @return
     */
    RepayResponse findRepayUser(Integer startTime, Integer endTime, Integer currPage, Integer pageSize);

    /**
     *批量添加待回款用户信息
     * @param resultList
     */
    void addRepayUserList(List<RepaymentPlanVO> resultList);

    /**
     * 查询本月待回款是否已经统计
     * @return
     */
    IntegerResponse countRepayUserList();

    /**
     * @Author walter.limeng
     * @Description //投屏数据修复，获取2019年3月1号，2号，3号的充值数据
     * @Date 15:24 2019-04-10
     * @Param [startIndex 开始标识, endIndex 结束表示]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/

    List<ScreenDataBean> getRechargeList(Integer startIndex, Integer endIndex);
}
