package com.hyjf.am.user.dao.mapper.customize.callcenter;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.user.dao.model.customize.callcenter.CallcenterAccountHuifuCustomize;
import com.hyjf.am.user.dao.model.customize.callcenter.CallcenterUserBaseCustomize;

/**
 * @author libin
 * @version CallCenterCustomizeMapper, v0.1 2018/5/8 14:14
 */
public interface CallCenterAccountHuifuMapper {
    /**
     * @param mobiles
     * @return
     */
    List<CallcenterAccountHuifuCustomize> findHuifuTiedcardInfo(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest);
}
