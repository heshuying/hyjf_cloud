/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.authquery;

import com.hyjf.cs.user.beans.AutoStateQueryRequest;
import com.hyjf.cs.user.beans.AutoStateQueryResultBean;
import com.hyjf.cs.user.service.BaseService;

/**
 * @author zhangqingqing
 * @version AutoStateQueryService, v0.1 2018/6/12 14:00
 */
public interface AutoStateQueryService extends BaseService {

    AutoStateQueryResultBean queryStatus(AutoStateQueryRequest autoStateQuery);
}
