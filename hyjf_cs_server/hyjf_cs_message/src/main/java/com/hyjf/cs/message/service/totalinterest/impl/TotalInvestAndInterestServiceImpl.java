/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.totalinterest.impl;

import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.cs.message.bean.ic.TotalInvestAndInterestEntity;
import com.hyjf.cs.message.client.AmTradeClient;
import com.hyjf.cs.message.mongo.ic.TotalInvestAndInterestMongoDao;
import com.hyjf.cs.message.service.totalinterest.TotalInvestAndInterestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fq
 * @version TotalInvestAndInterestServiceImpl, v0.1 2018/7/31 11:20
 */
@Service
public class TotalInvestAndInterestServiceImpl implements TotalInvestAndInterestService {
    @Autowired
    private TotalInvestAndInterestMongoDao mongoDao;
    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public void updateData() {
        TotalInvestAndInterestVO vo = amTradeClient.getTotalInvestAndInterest();
        if (vo != null) {
            TotalInvestAndInterestEntity entity = new TotalInvestAndInterestEntity();
            BeanUtils.copyProperties(vo, entity);
            mongoDao.save(entity);
        }
    }
}
