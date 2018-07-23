/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.security.impl;

import com.hyjf.cs.message.bean.ic.TotalInvestAndInterestEntity;
import com.hyjf.cs.message.mongo.ic.TotalInvestAndInterestMongoDao;
import com.hyjf.cs.message.service.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fq
 * @version SecurityServiceImpl, v0.1 2018/7/20 14:52
 */
@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private TotalInvestAndInterestMongoDao mongoDao;

    @Override
    public BigDecimal selectTotalInvest() {
        List<TotalInvestAndInterestEntity> list = mongoDao.find(new Query());
        if (!CollectionUtils.isEmpty(list)) {
            TotalInvestAndInterestEntity entity = list.get(0);
            return entity.getTotalInvestAmount();
        }
        return null;
    }

    @Override
    public BigDecimal selectTotalInterest() {
        List<TotalInvestAndInterestEntity> list = mongoDao.find(new Query());
        if (!CollectionUtils.isEmpty(list)) {
            TotalInvestAndInterestEntity entity = list.get(0);
            return entity.getTotalInterestAmount();
        }
        return null;
    }

    @Override
    public int selectTotalTradeSum() {
        List<TotalInvestAndInterestEntity> list = mongoDao.find(new Query());
        if (!CollectionUtils.isEmpty(list)) {
            TotalInvestAndInterestEntity entity = list.get(0);
            return entity.getTotalInvestNum();
        }
        return 0;
    }
}
