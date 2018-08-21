/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.landingpage.impl;

import com.hyjf.cs.message.bean.ic.TotalInvestAndInterestEntity;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.mongo.ic.TotalInvestAndInterestMongoDao;
import com.hyjf.cs.message.result.LandingPageResulltVO;
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
    @Autowired
    private AmUserClient amUserClient;
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

    @Override
    public LandingPageResulltVO getUserData(){
        LandingPageResulltVO landingPageResulltVO = new LandingPageResulltVO();
        //累计收益(亿元)
        BigDecimal profitSum = selectInterestSum().divide(new BigDecimal("100000000")).setScale(0, BigDecimal.ROUND_DOWN);

        //累计投资者(万人),等同于累计交易笔数
        Integer serveUserSum = amUserClient.countAllUser();
        if (serveUserSum == null){
            serveUserSum = 0;
        }else {
            serveUserSum = serveUserSum / 10000;
        }

        landingPageResulltVO.setProfitSum(profitSum);
        landingPageResulltVO.setServeUserSum(serveUserSum);
        return landingPageResulltVO;
    }
}
