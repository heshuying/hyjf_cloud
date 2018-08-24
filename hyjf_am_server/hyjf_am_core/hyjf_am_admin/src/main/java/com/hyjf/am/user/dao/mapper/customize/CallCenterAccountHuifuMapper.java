package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.user.dao.model.customize.CallcenterAccountHuifuCustomize;

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
