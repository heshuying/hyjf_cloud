/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.promotion;

import java.util.List;

import com.hyjf.am.resquest.admin.PlatformCountRequest;
import com.hyjf.am.trade.dao.model.customize.PlatformCountCustomize;

/**
 * @author fq
 * @version PlatformCountService, v0.1 2018/8/9 15:26
 */
public interface PlatformCountService {
    /**
     * 获取列表
     * @param request
     * @return
     */
    List<PlatformCountCustomize> searchAction(PlatformCountRequest request);
}
