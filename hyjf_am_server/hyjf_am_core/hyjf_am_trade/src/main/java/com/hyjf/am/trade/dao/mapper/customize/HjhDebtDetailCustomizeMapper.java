package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.HjhDebtDetail;

import java.util.List;

/**
 * @author xiasq
 * @version packinfo, v0.1 2018/6/11 11:18
 */
public interface HjhDebtDetailCustomizeMapper {

	/**
	 * 根据计划加入订单号,查询供清算使用的债权明细
	 *
	 * @param accedeOrderId
	 * @return
	 */
	List<HjhDebtDetail> selectDebtDetailForLiquidation(String accedeOrderId);

	/**
	 * 查询分期还款最近一期已还款的债权明细
	 *
	 * @param orderId
	 * @return
	 * @author renxingchen
	 */
	HjhDebtDetail selectLastDebtDetailRepayed(String orderId);

	/**
	 * 将已经清算的债权清算状态修改为1
	 * 
	 * @param orderId
	 * @return
	 */
	int updateDetailDelFlagToOne(String orderId);

	/**
	 * 查询当前计息周期的债权详情
	 *
	 * @param orderId
	 * @return
	 * @author renxingchen
	 */
	HjhDebtDetail selectDebtDetailCurRepayPeriod(String orderId);

	/**
	 * 根据投资订单号和还款期数 查询债权详情
	 *
	 * @param orderId
	 * @param i
	 * @return
	 * @author renxingchen
	 */
	HjhDebtDetail selectDebtDetailLastRepay(String orderId, int repayPeriod);

	/**
	 * 查询投资订单号未还款的债权详情
	 * 
	 * @param orderId
	 * @return
	 */
	List<HjhDebtDetail> selectDebtDetailNoRepay(String orderId);

}
