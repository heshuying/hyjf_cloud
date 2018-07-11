/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import java.util.List;

import com.hyjf.am.response.admin.AccedeListResponse;
import com.hyjf.am.resquest.admin.AccedeListRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.hjh.AccedeListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeSumVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistDetailVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

/**
 * @author libin
 * @version AccedeListClient.java, v0.1 2018年7月7日 下午3:29:13
 */
public interface AccedeListClient {
	
	/**
	 * 检索加入明细列表
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	AccedeListResponse getAccedeListByParam(AccedeListRequest form);
	
	/**
	 * 检索加入明细列表不分页
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	List<AccedeListCustomizeVO> getAccedeListByParamWithoutPage(AccedeListRequest form);
	
	/**
	 * 检索加入明细列表列总计
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	HjhAccedeSumVO getCalcSumByParam(AccedeListRequest form);
	
	
	/**
	 * 通过userid获取user
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	UserVO getUserByUserId(int userId);
	
	/**
	 * 通过加入订单号查询法大大协议表
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	List<TenderAgreementVO> selectTenderAgreementByNid(String planOrderId);
	
	/**
	 * 通过
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	UserInfoVO selectUsersInfoByUserId(int userid);
	
	/**
	 * 更新协议发送状态
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	int updateSendStatusByParam(AccedeListRequest request);
	
	/**
	 * 查询用户投资信息
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	UserHjhInvistDetailVO selectUserHjhInvistDetail(AccedeListRequest request);
}
