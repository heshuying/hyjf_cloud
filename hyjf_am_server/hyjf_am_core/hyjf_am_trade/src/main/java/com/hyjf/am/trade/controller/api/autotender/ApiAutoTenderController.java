/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.autotender;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.resquest.api.AutoTenderComboRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.api.autotender.ApiAutoTenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author libin
 * 第三方出借散标
 * @version ApiAutoTenderController.java, v0.1 2018年8月27日 上午10:03:39
 */
@RestController
@RequestMapping("/am-trade/autotender")
public class ApiAutoTenderController extends BaseController{
	
    @Autowired
    private ApiAutoTenderService apiAutoTenderService;
    
	/**
	 * 
	 * 出借预插入
	 * 
	 * @param borrowNid
	 * @param orderId
	 * @param userId
	 * @param account
	 * @param ip
	 * @return
	 * @author Administrator
	 * @throws Exception
	 */
    @RequestMapping("/updatetenderlog")
    private IntegerResponse updateTenderLog(@RequestBody AutoTenderComboRequest autoTenderComboRequest) {
    	Integer flg = apiAutoTenderService.updateTenderLog(autoTenderComboRequest);
        return new IntegerResponse(flg);
    }
}
