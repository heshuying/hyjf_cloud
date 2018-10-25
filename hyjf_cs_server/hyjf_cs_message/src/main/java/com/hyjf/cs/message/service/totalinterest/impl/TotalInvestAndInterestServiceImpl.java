/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.totalinterest.impl;

import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.cs.message.bean.ic.TotalInvestAndInterestEntity;
import com.hyjf.cs.message.client.AmTradeClient;
import com.hyjf.cs.message.mongo.ic.TotalInvestAndInterestMongoDao;
import com.hyjf.cs.message.service.totalinterest.TotalInvestAndInterestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author fq
 * @version TotalInvestAndInterestServiceImpl, v0.1 2018/7/31 11:20
 */
@Service
public class TotalInvestAndInterestServiceImpl implements TotalInvestAndInterestService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TotalInvestAndInterestMongoDao mongoDao;
    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public void updateData() {
        TotalInvestAndInterestVO vo = amTradeClient.getTotalInvestAndInterest();
        TotalInvestAndInterestEntity entity = mongoDao.findOne(new Query());
        // 第一次插入
        if (entity == null) {
            entity = new TotalInvestAndInterestEntity();
        }
        entity.setTotalInvestAmount(vo.getTotalInvestAmount());
        entity.setTotalInvestNum(vo.getTotalInvestNum());
        entity.setTotalInterestAmount(vo.getTotalInterestAmount());
        entity.setHjhTotalInvestAmount(vo.getHjhTotalInvestAmount());
        entity.setHjhTotalInvestNum(vo.getHjhTotalInvestNum());
        entity.setHjhTotalInterestAmount(vo.getHjhTotalInterestAmount());
        logger.info("待更新数据: {}", entity);
        mongoDao.save(entity);
    }


    @Override
    public BigDecimal selectTenderSum() {
        TotalInvestAndInterestEntity entity = mongoDao.findOne(new Query());
        if (entity != null) {
            return entity.getTotalInvestAmount();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal selectInterestSum() {
        TotalInvestAndInterestEntity entity = mongoDao.findOne(new Query());
        if (entity != null) {
            return entity.getTotalInterestAmount();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public int selectTotalTenderSum() {
        TotalInvestAndInterestEntity entity = mongoDao.findOne(new Query());
        if (entity != null) {
            return entity.getTotalInvestNum();
        }
        return 0;
    }


}
