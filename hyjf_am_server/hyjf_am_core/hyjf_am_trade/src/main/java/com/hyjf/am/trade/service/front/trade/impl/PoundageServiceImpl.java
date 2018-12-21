/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.trade.impl;

import com.hyjf.am.trade.dao.model.customize.TimerPoundageDetailListCustomize;
import com.hyjf.am.trade.service.front.trade.PoundageService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wangjun
 * @version PoundageServiceImpl, v0.1 2018/7/28 10:09
 */
@Service
public class PoundageServiceImpl extends BaseServiceImpl implements PoundageService {
    /**
     * 手续费分账数据插入
     */
    @Override
    public void insertPoundage() {
        // 查询出来列表
        List<TimerPoundageDetailListCustomize> poundageDetailListCustomizeList = poundageDetailCustomizeMapper.selectTimerPoundageDetailList();
        if(!CollectionUtils.isEmpty(poundageDetailListCustomizeList)){
            // 插入手续费分账明细数据
            poundageDetailCustomizeMapper.insertTimerPoundageDetailList(poundageDetailListCustomizeList);
        }
        // 根据上一方法的明细数据，插入手续费分账总数据
        poundageDetailCustomizeMapper.insertTimerPoundageList();
    }
}
