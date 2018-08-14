/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.landingpage.impl;

import com.hyjf.cs.user.client.AmDataCollectClient;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.landingpage.LandingPageService;
import com.hyjf.cs.user.vo.LandingPageResulltVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author wangjun
 * @version LandingPageServiceImpl, v0.1 2018/7/30 16:39
 */
@Service
public class LandingPageServiceImpl extends BaseUserServiceImpl implements LandingPageService {
    @Autowired
    AmDataCollectClient amDataCollectClient;

    @Override
    public LandingPageResulltVO getUserData(){
        LandingPageResulltVO landingPageResulltVO = new LandingPageResulltVO();
        //累计收益(亿元)
        BigDecimal profitSum = BigDecimal.ZERO;
        //累计投资者(万人),等同于累计交易笔数
        Integer serveUserSum = null;

        profitSum = amDataCollectClient.selectInterestSum().divide(new BigDecimal("100000000")).setScale(0, BigDecimal.ROUND_DOWN);
        serveUserSum = amUserClient.countAllUser();

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
