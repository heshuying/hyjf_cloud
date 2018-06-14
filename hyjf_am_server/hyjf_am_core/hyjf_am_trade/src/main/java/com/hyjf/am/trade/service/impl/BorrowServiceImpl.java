/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowConfigMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowFinmanNewChargeMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowManinfoMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version BorrowServiceImpl, v0.1 2018/6/13 18:53
 */
@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowFinmanNewChargeMapper borrowFinmanNewChargeMapper;

    @Autowired
    private BorrowConfigMapper borrowConfigMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private BorrowManinfoMapper borrowManinfoMapper;

    @Override
    public BorrowFinmanNewCharge selectBorrowApr(BorrowFinmanNewChargeRequest request) {
        BorrowFinmanNewChargeExample example = new BorrowFinmanNewChargeExample();
        BorrowFinmanNewChargeExample.Criteria cra = example.createCriteria();
        cra.andProjectTypeEqualTo(request.getBorrowClass());
        cra.andInstCodeEqualTo(request.getInstCode());
        cra.andAssetTypeEqualTo(request.getAssetType());
        cra.andManChargeTimeTypeEqualTo(request.getQueryBorrowStyle());
        cra.andManChargeTimeEqualTo(request.getBorrowPeriod());
        cra.andStatusEqualTo(0);

        List<BorrowFinmanNewCharge> list = this.borrowFinmanNewChargeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public BorrowConfig getBorrowConfigByConfigCd(String configCd) {
        BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(configCd);
        return borrowConfig;
    }

    @Override
    public int insertBorrow(BorrowWithBLOBs borrow) {
        return borrowMapper.insertSelective(borrow);
    }

    @Override
    public int insertBorrowManinfo(BorrowManinfo borrowManinfo) {
         return borrowManinfoMapper.insertSelective(borrowManinfo);
    }
}
