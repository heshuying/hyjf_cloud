package com.hyjf.admin.service.impl;

import com.hyjf.am.response.admin.AdminTransferExceptionLogResponse;
import com.hyjf.am.vo.admin.TransferExceptionLogVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.TransferExceptionLogService;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;

/**
 * 异常中心-银行转账异常 Create by jijun 20180710
 * service接口实现类
 */
@Service
public class TransferExceptionLogServiceImpl extends BaseAdminServiceImpl implements TransferExceptionLogService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public AdminTransferExceptionLogResponse getRecordList(TransferExceptionLogVO request) {
       return amTradeClient.getAdminTransferExceptionLogCustomizeList(request);
    }

    @Override
    public Integer countRecord(TransferExceptionLogVO request) {
        return amTradeClient.getAdminTransferExceptionLogCustomizeCountRecord(request);
    }

    @Override
    public int updateRecordByUUID(TransferExceptionLogVO request) {
        return amTradeClient.updateTransferExceptionLogByUUID(request);
    }

    @Override
    public TransferExceptionLogVO getRecordByUUID(String uuid) {
        return amTradeClient.getTransferExceptionLogByUUID(uuid);
    }

    /**
     * 转账成功后续处理
     * @return
     */
    @Override
    public boolean transferAfter(JSONObject jsonObject) {
        return amTradeClient.transferAfter(jsonObject);
    }

    /**
     * 通过userId获取UserInfoCustomize
     * @param userId
     * @return
     */
    @Override
    public UserInfoCustomizeVO queryUserInfoByUserId(Integer userId) {
        return amUserClient.getUserInfoCustomizeByUserId(userId);
    }

    /**
     * 获取优惠券还款记录
     * jijun 20180711
     */
	@Override
	public CouponRecoverVO getCouponRecover(Integer recoverId) {
		return amTradeClient.getCouponRecoverByPrimaryKey(recoverId);
	}

    /**
     * 取得优惠券投资信息
     * @param nid
     * @return
     */
    @Override
    public BorrowTenderCpnVO getCouponTenderInfoByNid(String nid) {
        return amTradeClient.getCouponTenderInfoByNid(nid);
    }

    /**
     * 获取userInfo
     * @param userId
     * @return
     */
    @Override
    public UserInfoVO getUserInfoByUserId(Integer userId) {
        return amUserClient.findUsersInfoById(userId);
    }

    /**
     * 查找用户的推荐人
     * @param userId
     * @return
     */
    @Override
    public SpreadsUserVO getSpreadsUserByUserId(Integer userId) {
        return amUserClient.searchSpreadsUserByUserId(userId);
    }

    /**
     *  获取员工
     * @param userId
     * @return
     */
    @Override
    public EmployeeCustomizeVO selectEmployeeByUserId(Integer userId) {
        return amUserClient.searchEmployeeBuUserId(userId);
    }

    /**
     * 获取user
     * @param userId
     * @return
     */
    @Override
    public UserVO getUserByUserId(Integer userId) {
        return amUserClient.findUserById(userId);
    }


}
