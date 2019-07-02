/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.funddetail;

import com.alibaba.fastjson.JSON;
import com.hyjf.wbs.WbsApplication;
import com.hyjf.wbs.common.EntUtmIds;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.qvo.FundDetailsQO;
import com.hyjf.wbs.qvo.FundDetailsVO;
import com.hyjf.wbs.qvo.WbsCommonExQO;
import com.hyjf.wbs.qvo.WbsCommonQO;
import com.hyjf.wbs.sign.WbsSignUtil;
import com.hyjf.wbs.trade.service.FundDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    private WbsConfig wbsConfig;

//    @Test
    public void testProperties(){
        System.out.println(EntUtmIds.getUtmId("8001"));
    }

//    @Test
    public void testFundService(){

        FundDetailsQO qo=new FundDetailsQO();
        qo.setStartTime("2019/05/28 09:33:05");
        qo.setEndTime("2019/05/28 23:33:05");
        qo.setEntId(8002);
        qo.setBusinessType(2);

        List<FundDetailsVO> lst=fundDetailsService.queryFundDetails(qo);
        System.out.println("**************"+lst);
    }

    @Test
    public void testCreateSign() throws UnsupportedEncodingException {
        WbsCommonQO qo=new WbsCommonQO();
        qo.setApp_key(wbsConfig.getAppKey());
        qo.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        qo.setName("hyjf.funddetail.batchsync");
        qo.setVersion("");
        qo.setAccess_token("");

        FundDetailsQO detailsQO=new FundDetailsQO();
        detailsQO.setStartTime("2019/05/14 09:33:05");
        detailsQO.setEndTime("2019/05/14 23:33:05");
        detailsQO.setEntId(8001);
        detailsQO.setBusinessType(1);

        String json=JSON.toJSONString(detailsQO);
        json=URLEncoder.encode(json,"utf-8");

        qo.setData(json);

        String sign=WbsSignUtil.encrypt(qo,wbsConfig.getAppSecret());

        WbsCommonExQO exQO=new WbsCommonExQO();
        BeanUtils.copyProperties(qo,exQO);
        exQO.setSign(sign);

        System.out.println(JSON.toJSONString(exQO));
    }
}
