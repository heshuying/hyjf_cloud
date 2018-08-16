package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowConfigMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowConfig;
import com.hyjf.am.trade.service.front.borrow.BorrowConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hesy
 * @version BorrowConfigServiceImpl, v0.1 2018/7/4 13:52
 */
@Service
public class BorrowConfigServiceImpl implements BorrowConfigService {
    @Resource
    BorrowConfigMapper borrowConfigMapper;

    @Override
    public BorrowConfig selectBorrowConfigByCode(String code){
        BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(code);
        return borrowConfig;
    }
}
