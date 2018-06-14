/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.client;

import com.hyjf.am.resquest.callcenter.CallCenterAccountDetailRequest;
import com.hyjf.am.vo.callcenter.CallCenterAccountDetailVO;
import java.util.List;

/**
 * @author wangjun
 * @version AmCallcenterBaseClient, v0.1 2018/6/6 10:02
 */
public interface SrchCapitalInfoClient {
    List<CallCenterAccountDetailVO> queryAccountDetails(CallCenterAccountDetailRequest callCenterAccountDetailRequest);
}
