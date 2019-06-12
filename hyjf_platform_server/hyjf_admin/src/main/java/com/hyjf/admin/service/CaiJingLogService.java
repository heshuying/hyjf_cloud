/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.CaiJingLogResponse;
import com.hyjf.am.resquest.admin.CaiJingLogRequest; /**
 * @author yaoyong
 * @version CaiJingLogService, v0.1 2019/6/10 9:44
 */
public interface CaiJingLogService {

    /**
     * 查询财经日志列表
     * @param request
     * @return
     */
    CaiJingLogResponse queryCaiJingLog(CaiJingLogRequest request);

    /**
     * 重新报送
     * @param request
     * @return
     */
    BooleanResponse reQueryCaiJingLog(CaiJingLogRequest request);
}
