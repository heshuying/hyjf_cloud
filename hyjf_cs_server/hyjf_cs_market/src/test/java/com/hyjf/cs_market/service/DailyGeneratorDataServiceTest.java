package com.hyjf.cs_market.service;

import com.hyjf.cs.market.CsMarketApplication;
import com.hyjf.cs.market.service.DailyGeneratorDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiasq
 * @version DailyGeneratorDataServiceTest, v0.1 2019/5/7 10:31
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsMarketApplication.class)
public class DailyGeneratorDataServiceTest {

    @Autowired
    DailyGeneratorDataService service;

    @Test
    public void generatorSellDailyTest() {
        service.generatorSellDaily();
    }
}
