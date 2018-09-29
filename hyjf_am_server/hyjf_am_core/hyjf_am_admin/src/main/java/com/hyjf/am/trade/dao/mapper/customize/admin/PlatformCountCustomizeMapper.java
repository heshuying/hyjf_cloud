/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.PlatformCountRequest;
import com.hyjf.am.trade.dao.model.customize.PlatformCountCustomize;

import java.util.List;

/**
 * @author fq
 * @version PlatformCountCustomizeMapper, v0.1 2018/8/10 10:23
 */
public interface PlatformCountCustomizeMapper {
    /**
     * 获取列表
     * @param platformCountCustomize
     * @return
     */
    List<PlatformCountCustomize> selectList(PlatformCountRequest platformCountCustomize);

    List<PlatformCountCustomize> selectRegistNumber(PlatformCountRequest platformCountCustomize);

    List<PlatformCountCustomize> selectAccountNumber(PlatformCountRequest platformCountCustomize);
}
