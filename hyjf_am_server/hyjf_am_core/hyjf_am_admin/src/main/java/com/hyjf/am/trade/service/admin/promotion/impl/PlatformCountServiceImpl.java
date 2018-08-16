/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.promotion.impl;

import com.hyjf.am.resquest.admin.PlatformCountRequest;
import com.hyjf.am.trade.dao.mapper.customize.admin.PlatformCountCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.PlatformCountCustomize;
import com.hyjf.am.trade.service.admin.promotion.PlatformCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fq
 * @version PlatformCountServiceImpl, v0.1 2018/8/9 15:27
 */
@Service
public class PlatformCountServiceImpl implements PlatformCountService {
    @Autowired
    private PlatformCountCustomizeMapper customizeMapper;
    @Override
    public List<PlatformCountCustomize> searchAction(PlatformCountRequest request) {
        return customizeMapper.selectList(request);
    }
}
