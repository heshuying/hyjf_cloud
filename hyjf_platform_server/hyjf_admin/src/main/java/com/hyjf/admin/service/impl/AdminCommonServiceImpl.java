/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AdminCommonClient;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version AdminCommonServiceImpl, v0.1 2018/7/3 10:25
 */
@Service
public class AdminCommonServiceImpl implements AdminCommonService {
    @Autowired
    AdminCommonClient adminCommonClient;

    @Override
    public Map<String, String> getParamNameMap(String param) {
        Map<String, String> resultMap = CacheUtil.getParamNameMap(param);
        return resultMap;
    }

    @Override
    public List<BorrowStyleVO> selectBorrowStyleList() {
        return adminCommonClient.selectBorrowStyleList();
    }

    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigList() {
        return adminCommonClient.selectHjhInstConfigList();
    }
}
