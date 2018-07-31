/**
 * Description:我的投资service接口
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */
package com.hyjf.cs.user.service.myproject;

import com.hyjf.am.resquest.app.AppProjectContractDetailBeanRequest;
import com.hyjf.am.resquest.app.AppRepayPlanListBeanRequest;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.app.*;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.AppAlreadyRepayListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.AppTenderCreditRecordListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.common.service.BaseService;

import java.util.List;
import java.util.Map;

public interface MyInvestProjectService extends BaseService {

	/**
	 * 我的投资（已回款）
	 *
	 * @param params
	 * @return
	 */
	List<AppAlreadyRepayListCustomizeVO> selectAlreadyRepayList(AssetManageBeanRequest request);

	/**
	 * 统计我的投资（已回款）项目总数
	 *
	 * @param params
	 * @return
	 */
	int countAlreadyRepayListRecordTotal(AssetManageBeanRequest request);

	/**
	 * 还款计划
	 *
	 * @param params
	 * @return
	 */
	List<AppRepayPlanListCustomizeVO> selectRepayPlanList(AppRepayPlanListBeanRequest params);

	/**
	 * 统计还款计划总数
	 *
	 * @param params
	 * @return
	 */

	int countRepayPlanListRecordTotal(AppRepayPlanListBeanRequest params);

	BorrowVO selectBorrowByBorrowNid(String borrowNid);

	BorrowStyleVO selectBorrowStyleByStyle(String borrowStyle);

	int countRepayRecoverListRecordTotal(AppRepayPlanListBeanRequest params);

	List<AppRepayPlanListCustomizeVO> selectRepayRecoverList(AppRepayPlanListBeanRequest params);

	List<AppProjectContractRecoverPlanCustomizeVO> selectProjectContractRecoverPlan(AppProjectContractDetailBeanRequest params);

	AppProjectContractDetailCustomizeVO selectProjectContractDetail(AppProjectContractDetailBeanRequest params);

	List<AppRepayPlanListCustomizeVO> selectCouponRepayRecoverList(AppRepayPlanListBeanRequest params);

	int countCouponRepayRecoverListRecordTotal(AppRepayPlanListBeanRequest params);

	String selectReceivedInterest(AppRepayPlanListBeanRequest params);

	/**
	 *
	 * 获取回款中的项目详情
	 *
	 * @author liuyang
	 * @param params
	 * @return
	 */
	public AppRepayDetailCustomizeVO selectRepayDetail(Map<String, Object> params);

	/**
	 *
	 * 优惠券投资项目详情
	 *
	 * @author liuyang
	 * @param params
	 * @return
	 */
	public AppRepayDetailCustomizeVO selectCouponRepayDetail(Map<String, Object> params);

	/**
	 *
	 * 获取投资中的项目详情
	 *
	 * @author liuyang
	 * @param params
	 * @return
	 */
	public AppRepayDetailCustomizeVO selectInvestProjectDetail(Map<String, Object> params);

	/**
	 *
	 * 获取投资中的优惠券项目详情
	 *
	 * @author liuyang
	 * @param params
	 * @return
	 */
	public AppRepayDetailCustomizeVO selectCouponInvestProjectDetail(Map<String, Object> params);

	/***
	 *
	 * 获取已回款的项目详情
	 *
	 * @author liuyang
	 * @param params
	 * @return
	 */
	public AppRepayDetailCustomizeVO selectRepayedProjectDetail(Map<String, Object> params);

	/**
	 *
	 * 获取已回款的优惠券项目详情
	 *
	 * @author liuyang
	 * @param params
	 * @return
	 */
	public AppRepayDetailCustomizeVO selectCouponRepayedProjectDetail(Map<String, Object> params);

	String getInvestingData(String userId, String host, String page, String pageSize);

	String getRepayedData(String userId, String host, String page, String pageSize);

	String getRepayData(String userId, String host, String hostContact, String page, String pageSize);

	CouponConfigVO getCouponConfigByOrderId(String nid);

	List<WebUserInvestListCustomizeVO> selectUserInvestList(String borrowNid, Integer userId, String nid, int limitStart,
															int limitEnd);

	/**
	 * 判断用户所在渠道是否允许债转
	 * @param userId
	 * @return
	 */
	boolean isAllowChannelAttorn(Integer userId);

	AccountVO getAccount(Integer userId);

	int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest request);

	List<CurrentHoldObligatoryRightListCustomizeVO> selectCurrentHoldObligatoryRightList(AssetManageBeanRequest params);

	int countCreditRecord(AssetManageBeanRequest params);

	List<AppTenderCreditRecordListCustomizeVO> searchCreditRecordList(AssetManageBeanRequest params);

	AppProjectDetailCustomizeVO selectProjectDetail(String borrowNid);

	UserInfoVO getUserInfoByUserId(Integer userId);

	BorrowCreditVO selectCreditTenderByCreditNid(String creditNid);

	List<AppTenderCreditRepayPlanListCustomizeVO> selectTenderCreditRepayPlanList(AppRepayPlanListBeanRequest params);

	List<AppTenderCreditRepayPlanListCustomizeVO> selectTenderCreditRepayRecoverPlanList(AppRepayPlanListBeanRequest params);

    List<AppTenderToCreditListCustomizeVO> selectTenderToCreditList(Map<String,Object> params);
}
