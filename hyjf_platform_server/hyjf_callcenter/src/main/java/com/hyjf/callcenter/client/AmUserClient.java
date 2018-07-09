package com.hyjf.callcenter.client;

import com.hyjf.am.resquest.callcenter.CallCenterServiceUsersRequest;
import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.vo.callcenter.CallCenterUserBaseVO;
import com.hyjf.am.vo.callcenter.CallcenterAccountHuifuVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.UserBean;

import java.util.List;

/**
 * @author wangjun
 * @version AmUserClient, v0.1 2018/7/6 17:12
 */
public interface AmUserClient {
    /**
     * 查询江西银行绑卡
     * @param user
     * @return
     */
    List<BankCardVO> getTiedCardOfAccountBank(UserVO user);

    /**
     * 取得汇直投投资信息
     * 同步另外文件BorrowInvestCustomizeMapper
     * @param callcenterAccountHuifuRequest
     * @return
     */
    List<CallcenterAccountHuifuVO> selectBankCardList(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest);

    /**
     * 获取user信息
     * @param bean
     * @return
     */
    UserVO getUser(UserBean bean);

    /**
     * 查询呼叫中心未分配客服的用户（复投用户筛选）
     * @param callCenterUserInfoRequest
     * @return
     */
    List<CallCenterUserBaseVO> selectNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);
    /**
     * 查询呼叫中心未分配客服的用户（流失用户筛选）
     * @param callCenterUserInfoRequest
     * @return
     */
    List<CallCenterUserBaseVO> selectNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);
    /**
     * 查询呼叫中心未分配客服的用户
     * @param callCenterUserInfoRequest
     * @return
     */
    List<CallCenterUserBaseVO> selectNoServiceUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * 更新呼叫中心用户分配客服的状态
     * @param callCenterServiceUsersRequest
     * @return Integer
     * @author wangjun
     */
    Integer executeRecord(CallCenterServiceUsersRequest callCenterServiceUsersRequest);

    /**
     * 查询用户基本信息
     * @param callCenterUserInfoRequest
     * @return
     */
    List<CallCenterUserBaseVO> selectUserList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * 查询用户详细信息
     * @param callCenterUserInfoRequest
     * @return
     */
    List<CallCenterUserBaseVO> selectUserDetailById(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * 查询优惠券内容
     * @param couponCode
     * @return String
     * @author wangjun
     */
    String getCouponContent(String couponCode);
}
