/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.task;

import com.hyjf.am.trade.dao.mapper.customize.trade.PoundageDetailCustomizeMapper;
import com.hyjf.am.trade.service.task.PoundageTimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangjun
 * @version PoundageTimerServiceImpl, v0.1 2018/6/21 15:49
 */
@Service
public class PoundageTimerServiceImpl implements PoundageTimerService {

    @Autowired
    PoundageDetailCustomizeMapper poundageDetailCustomizeMapper;
    public void insertPoundage(){
        // 插入手续费分账明细数据
        poundageDetailCustomizeMapper.insertTimerPoundageDetailList();

        // 根据上一方法的明细数据，插入手续费分账总数据
        poundageDetailCustomizeMapper.insertTimerPoundageList();
    }
}
