package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.user.dao.model.customize.CallcenterUserBaseCustomize;

/**
 * @author libin
 * @version CallCenterCustomizeMapper, v0.1 2018/5/8 14:14
 */
public interface CallCenterCustomizeMapper {
	
    /**
     * @param mobiles
     * @return
     */
    List<CallcenterUserBaseCustomize> findNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);
    
    /**
     * @param mobiles
     * @return
     */
    List<CallcenterUserBaseCustomize> findNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);
    
    /**
     * @param mobiles
     * @return
     */
    List<CallcenterUserBaseCustomize> findNoServiceUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest);

}
