/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.authquery;

import com.hyjf.cs.user.bean.AutoStateQueryRequest;
import com.hyjf.cs.user.bean.AutoStateQueryResultBean;
import com.hyjf.cs.user.service.BaseUserService;

/**
 * @author zhangqingqing
 * @version AutoStateQueryService, v0.1 2018/6/12 14:00
 */
public interface AutoStateQueryService extends BaseUserService {

    /**
     * 查询授权状态
     * @param autoStateQuery
     * @return
     */
    AutoStateQueryResultBean queryStatus(AutoStateQueryRequest autoStateQuery);
}
