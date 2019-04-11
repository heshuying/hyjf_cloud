package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.ScreenDataBean;
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

    /**
     * @Author walter.limeng
     * @Description //投屏数据修复，获取2019年3月1号，2号，3号的充值数据
     * @Date 15:24 2019-04-10
     * @Param [startIndex 开始标识, endIndex 结束表示]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/
    List<ScreenDataBean> getRechargeList(Integer startIndex, Integer endIndex);

    /**
     * @Author walter.tyy
     * @Description //投屏数据修复，获取2019年4月1号，2号，3号的计划退出回款
     * @Date 10:12 2019-04-11
     * @Param [paramMap]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/
    List<ScreenDataBean> getPlanRepayList(Integer startIndex, Integer endIndex);
    /**
     * @Author walter.tyy
     * @Description //投屏数据修复，获取2019年4月1号，2号，3号的计划投资
     * @Date 10:12 2019-04-11
     * @Param [paramMap]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/
    List<ScreenDataBean> getPlanTenderList(Integer startIndex, Integer endIndex);

    /**
     * @Author walter.tyy
     * @Description //投屏数据修复，获取2019年4月1号，2号，3号的散标承接
     * @Date 10:12 2019-04-11
     * @Param [paramMap]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/
    List<ScreenDataBean> getCreditTenderList(Integer startIndex, Integer endIndex);
    /**
     * @Author walter.limeng
     * @Description //投屏数据修复，获取2019年3月1号，2号，3号的提现数据
     * @Date 15:24 2019-04-10
     * @Param [startIndex 开始标识, endIndex 结束表示]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/
    List<ScreenDataBean> getWithdrawList(Integer startIndex, Integer endIndex);
}
