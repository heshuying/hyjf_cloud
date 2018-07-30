/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.landingpage.impl;

import com.hyjf.cs.message.bean.ic.TotalInvestAndInterestEntity;
import com.hyjf.cs.message.mongo.ic.TotalInvestAndInterestMongoDao;
import com.hyjf.cs.message.service.landingpage.LandingPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author wangjun
 * @version LandingPageImpl, v0.1 2018/7/30 16:46
 */
@Service
public class LandingPageServiceImpl implements LandingPageService {
    @Autowired
    private TotalInvestAndInterestMongoDao totalInvestAndInterestMongoDao;

    /**
     * 获取累计收益
     *
     * @return
     */
    @Override
    public BigDecimal selectInterestSum(){
        TotalInvestAndInterestEntity entity = totalInvestAndInterestMongoDao.findOne(new Query());
        if (entity != null) {
            return entity.getTotalInterestAmount();
        }
        return BigDecimal.ZERO;
    }
}
