package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.AdminTransferExceptionLogResponse;
import com.hyjf.am.vo.admin.TransferExceptionLogVO;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.user.*;

/**
 * admin 异常中心-银行转账异常 Create by jijun 20180710
 * service接口定义
 */
public interface TransferExceptionLogService extends BaseAdminService{

    /**
     * 
     * 获取转账异常列表
     * @author jijun
     * @return
     */
	AdminTransferExceptionLogResponse getRecordList(TransferExceptionLogVO request);

	/**
	 * 获取转账异常记录数
	 * @author jijun
	 * @return
	 */
	public Integer countRecord(TransferExceptionLogVO request);


	/**
	 *	更新转账信息
	 * @param request
	 * @return
	 */
    int updateRecordByUUID(TransferExceptionLogVO request);

	/**
	 * 获取银行转账异常通过uuid
	 * @param uuid
	 * @return
	 */
	TransferExceptionLogVO getRecordByUUID(String uuid);

	/**
	 * 转账成功后续处理
	 * @return
	 */
	boolean transferAfter(JSONObject jsonObject);

	/**
	 *
	 * @param userId
	 * @return
	 */
    UserInfoCustomizeVO queryUserInfoByUserId(Integer userId);

    /**
     * 获取优惠券还款记录
     * jijun 20180711
     */
	public CouponRecoverVO getCouponRecover(Integer recoverId);

	/**
	 * 取得优惠券投资信息
	 * @param tenderId
	 * @return
	 */
    BorrowTenderCpnVO getCouponTenderInfoByNid(String tenderId);

	/**
	 * 获取userInfo
	 * @param userId
	 * @return
	 */
	UserInfoVO getUserInfoByUserId(Integer userId);

	/**
	 * 查找用户的推荐人
	 * @param userId
	 * @return
	 */
	SpreadsUserVO getSpreadsUserByUserId(Integer userId);

	/**
	 * 获取员工信息
	 * @param userId
	 * @return
	 */
	EmployeeCustomizeVO selectEmployeeByUserId(Integer userId);

	/**
	 * 获取user
	 * @return
	 */
    UserVO getUserByUserId(Integer userId);
}