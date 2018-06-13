package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;
import java.util.Map;

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
    List<CallcenterUserBaseCustomize> findNoServiceFuTouUsersList(Map<String, Object> conditionMap);
    
    /**
     * @param mobiles
     * @return
     */
    List<CallcenterUserBaseCustomize> findNoServiceLiuShiUsersList(Map<String, Object> conditionMap);

}
