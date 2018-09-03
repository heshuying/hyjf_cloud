/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.trade.dao.mapper.customize.AdminPoundageCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.AdminPoundageCustomize;
import com.hyjf.am.trade.service.admin.TradePoundageService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: TradePoundageServiceImpl, v0.1 2018/9/3 15:20
 */
@Service
public class TradePoundageServiceImpl extends BaseServiceImpl implements TradePoundageService {

    @Autowired
    private AdminPoundageCustomizeMapper adminPoundageCustomizeMapper;

    @Override
    public int getPoundageCount(PoundageListRequest request) {
        return adminPoundageCustomizeMapper.getPoundageCount(request);
    }

    @Override
    public List<AdminPoundageCustomize> searchPoundageList(PoundageListRequest request) {
        return adminPoundageCustomizeMapper.getPoundageList(request);
    }
}
