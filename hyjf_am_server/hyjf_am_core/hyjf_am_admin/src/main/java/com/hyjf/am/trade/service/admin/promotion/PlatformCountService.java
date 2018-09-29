/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.promotion;

import com.hyjf.am.resquest.admin.PlatformCountRequest;
import com.hyjf.am.trade.dao.model.customize.PlatformCountCustomize;

import java.util.List;

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

    /**
     * 获取注册人数
     * @param request
     * @return
     */
    List<PlatformCountCustomize> searchRegistNumber(PlatformCountRequest request);

    /**
     * 获取开户数量
     * @param request
     * @return
     */
    List<PlatformCountCustomize> searchAccountNumber(PlatformCountRequest request);
}
