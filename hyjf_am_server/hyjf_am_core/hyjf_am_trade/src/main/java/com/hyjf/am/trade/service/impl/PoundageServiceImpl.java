/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.service.PoundageService;
import org.springframework.stereotype.Service;

/**
 * @author wangjun
 * @version PoundageServiceImpl, v0.1 2018/7/28 10:09
 */
@Service
public class PoundageServiceImpl extends BaseServiceImpl implements PoundageService {
    @Override
    public void insertPoundage() {
        // 插入手续费分账明细数据
        poundageDetailCustomizeMapper.insertTimerPoundageDetailList();
        // 根据上一方法的明细数据，插入手续费分账总数据
        poundageDetailCustomizeMapper.insertTimerPoundageList();
    }
}
