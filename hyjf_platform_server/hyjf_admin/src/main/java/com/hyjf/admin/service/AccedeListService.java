/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

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
 * @author Albert
 * @version AccedeListService.java, v0.1 2018年7月7日 下午3:04:29
 */
public interface AccedeListService {
	/**
     * 获取加入计划列表
     * @return
     */
	AccedeListResponse getAccedeListByParam(AccedeListRequest form);
	
	/**
     * 获取加入计划列表不分页
     * @return
     */
	List<AccedeListCustomizeVO> getAccedeListByParamWithoutPage(AccedeListRequest form);
	
	/**
     * 获取加入计划列表列总计
     * @return
     */
	HjhAccedeSumVO getCalcSumByParam(AccedeListRequest form);
	/**
	 * EMAIL入力后发送协议
	 * @author pcc
	 * @param userid
	 * @param planOrderId
	 * @param debtPlanNid
	 */
	String resendMessageAction(String userid, String planOrderId, String debtPlanNid,String sendEmail);
	
	/**
	 * 通过用户id获取用户信息   抽成共通
	 * @author pcc
	 * @param userid
	 * @param planOrderId
	 * @param debtPlanNid
	 */
	UserVO getUserByUserId(int userId);
	
	/**
	 * 通过计划订单号查询法大大协议列表
	 * @author pcc
	 * @param userid
	 * @param planOrderId
	 * @param debtPlanNid
	 */
	List<TenderAgreementVO> selectTenderAgreementByNid(String planOrderId);
	
	/**
	 * 通过userid获取用户详情
	 * @author pcc
	 * @param userid
	 * @param planOrderId
	 * @param debtPlanNid
	 */
	UserInfoVO getUsersInfoByUserId(int userid);
	
	/**
	 * 更新协议发送状态
	 * @author 
	 * @param userid
	 * @param planOrderId
	 * @param debtPlanNid
	 */
	int updateSendStatusByParam(AccedeListRequest request);
	
	/**
	 * PDF下载加脱敏
	 * @param tenderAgreement
	 * @param borrowNid
	 * @param transType
	 * @param instCode
	 */
	void updateSaveSignInfo(TenderAgreementVO tenderAgreement,String borrowNid, Integer transType, String instCode);
	
	/**
	 * 查询用户投资详情
	 * @param tenderAgreement
	 * @param borrowNid
	 * @param transType
	 * @param instCode
	 */
	UserHjhInvistDetailVO selectUserHjhInvistDetail(AccedeListRequest request);
}
