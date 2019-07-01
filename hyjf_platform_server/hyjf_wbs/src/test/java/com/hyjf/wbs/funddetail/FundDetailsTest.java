/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.funddetail;

import com.hyjf.wbs.WbsApplication;
import com.hyjf.wbs.common.EntUtmIds;
import com.hyjf.wbs.qvo.FundDetailsQO;
import com.hyjf.wbs.qvo.FundDetailsVO;
import com.hyjf.wbs.trade.service.FundDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author cui
 * @version FundDetailsTest, v0.1 2019/7/1 16:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WbsApplication.class)
public class FundDetailsTest {

    @Autowired
    private FundDetailsService fundDetailsService;

    @Test
    public void testProperties(){
        System.out.println(EntUtmIds.getUtmId("8001"));
    }

    @Test
    public void testFundService(){

        FundDetailsQO qo=new FundDetailsQO();
        qo.setStartTime("2019/05/28 09:33:05");
        qo.setEndTime("2019/05/28 23:33:05");
        qo.setEntId(8002);
        qo.setBusinessType(2);

        List<FundDetailsVO> lst=fundDetailsService.queryFundDetails(qo);
        System.out.println("**************"+lst);
    }
}
