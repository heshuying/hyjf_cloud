package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.user.dao.model.customize.CallcenterAccountHuifuCustomize;

import java.util.List;

/**
 * @author libin
 * @version CallCenterCustomizeMapper, v0.1 2018/5/8 14:14
 */
public interface CallCenterAccountHuifuMapper {
    /**
     * @param
     * @return
     */
    List<CallcenterAccountHuifuCustomize> findHuifuTiedcardInfo(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest);
}
