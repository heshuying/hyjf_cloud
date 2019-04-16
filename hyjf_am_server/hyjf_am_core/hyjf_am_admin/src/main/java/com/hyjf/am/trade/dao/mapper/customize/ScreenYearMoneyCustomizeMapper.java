/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.vo.trade.RepaymentPlanVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * @Author walter.tyy
     * @Description //投屏数据修复，获取2019年4月1号，2号，3号的计划投资
     * @Date 10:12 2019-04-11
     * @Param [paramMap]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/
    List<ScreenDataBean> getPlanTenderList(Map<String, Object> paramMap);
    /**
     * @Author walter.tyy
     * @Description //投屏数据修复，获取2019年4月1号，2号，3号的计划退出回款
     * @Date 10:12 2019-04-11
     * @Param [paramMap]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/
    List<ScreenDataBean> getPlanRepayList(Map<String, Object> paramMap);

    /**
     * @Author walter.tyy
     * @Description //投屏数据修复，获取2019年4月1号，2号，3号的散标承接
     * @Date 10:12 2019-04-11
     * @Param [paramMap]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/
    List<ScreenDataBean> getCreditTenderList(Map<String, Object> paramMap);
    /**
     * @Author walter.limeng
     * @Description //投屏数据修复，获取2019年3月1号，2号，3号的充值数据
     * @Date 10:12 2019-04-11
     * @Param [paramMap]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/
    List<ScreenDataBean> getRechargeList(Map<String, Object> paramMap);

    /**
     * @Author walter.limeng
     * @Description //投屏数据修复，获取2019年3月1号，2号，3号的提现数据
     * @Date 10:12 2019-04-11
     * @Param [paramMap]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/
    List<ScreenDataBean> getWithdrawList(Map<String, Object> paramMap);

    /**
     * @Author walter.limeng
     * @Description //投屏数据修复，获取2019年3月1号，2号，3号的还款成功数据
     * @Date 10:12 2019-04-11
     * @Param [paramMap]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/
    List<ScreenDataBean> getBorrowRecoverList(Map<String, Object> paramMap);

    /**
     * @Author walter.limeng
     * @Description //投屏数据修复，获取2019年3月1号，2号，3号的散标投资数据
     * @Date 10:12 2019-04-11
     * @Param [paramMap]
     * @return java.util.List<com.hyjf.am.resquest.trade.ScreenDataBean>
     **/
    List<ScreenDataBean> getBorrowTenderList(Map<String, Object> paramMap);
}
