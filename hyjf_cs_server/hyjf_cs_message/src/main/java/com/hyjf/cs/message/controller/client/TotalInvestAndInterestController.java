/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.totalinterest.TotalInvestAndInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 定时更新运营数据
 * @author fq
 * @version TotalInvestAndInterestController, v0.1 2018/7/31 11:17
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/totalinvestandinterest")
public class TotalInvestAndInterestController extends BaseController {
    @Autowired
    private TotalInvestAndInterestService service;

    @RequestMapping("/excute")
    public void excute() {
        service.updateData();
    }


    /**
     * 累计出借总额
     * @return
     */
    @RequestMapping("/selectTenderSum")
    BigDecimalResponse selectTenderSum(){
        return new BigDecimalResponse(service.selectTenderSum()) ;
    }

    /**
     * 累计收益
     * @return
     */
    @RequestMapping("/selectInterestSum")
    BigDecimalResponse selectInterestSum(){
        return new BigDecimalResponse(service.selectInterestSum());
    }

    /**
     * 累计出借笔数
     * @return
     */
    @RequestMapping("/selectTotalTenderSum")
    IntegerResponse selectTotalTenderSum(){
        return new IntegerResponse(service.selectTotalTenderSum());
    }

}
