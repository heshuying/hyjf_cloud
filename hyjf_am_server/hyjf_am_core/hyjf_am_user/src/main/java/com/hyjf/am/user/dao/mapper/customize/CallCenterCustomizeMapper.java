package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.user.dao.model.customize.CallcenterUserBaseCustomize;

import java.util.List;

/**
 * @author libin
 * @version CallCenterCustomizeMapper, v0.1 2018/5/8 14:14
 */
public interface CallCenterCustomizeMapper {

    /**
     * @param
     * @return
     */
    List<CallcenterUserBaseCustomize> findNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * @param
     * @return
     */
    List<CallcenterUserBaseCustomize> findNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * @param
     * @return
     */
    List<CallcenterUserBaseCustomize> findNoServiceUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * @param
     * @return
     */
    List<CallcenterUserBaseCustomize> findBasicUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * @param
     * @return
     */
    List<CallcenterUserBaseCustomize> findUserDetailById(CallCenterUserInfoRequest callCenterUserInfoRequest);

    /**
     * @param couponSource
     * @return
     */
    String getCouponContent(String couponSource);

}
