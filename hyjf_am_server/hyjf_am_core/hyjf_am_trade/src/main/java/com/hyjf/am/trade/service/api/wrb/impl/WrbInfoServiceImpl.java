/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.wrb.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.trade.dao.mapper.customize.WrbQueryCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowListCustomize;
import com.hyjf.am.trade.service.api.wrb.WrbInfoService;

/**
 * @author fq
 * @version WrbInfoServiceImpl, v0.1 2018/9/25 11:12
 */
@Service
public class WrbInfoServiceImpl implements WrbInfoService {
    @Autowired
    private WrbQueryCustomizeMapper wrbQueryCustomizeMapper;

    @Override
    public List<WrbBorrowListCustomize> borrowList(String borrowNid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowClass", "");

        // 定向标过滤
        params.put("publishInstCode", "");
        if (StringUtils.isNotBlank(borrowNid)) {
            params.put("borrowNid", borrowNid);
        }else{
            params.put("projectType", "HZT");
            params.put("status", 2);// 获取 投资中
        }
        List<WrbBorrowListCustomize> customizeList = wrbQueryCustomizeMapper.searchBorrowListByNid(params);
        for (WrbBorrowListCustomize wrbBorrowListCustomize : customizeList) {
            String url = wrbBorrowListCustomize.getInvest_url();
            wrbBorrowListCustomize.setInvest_url(url);
        }
        return customizeList;
    }
}
