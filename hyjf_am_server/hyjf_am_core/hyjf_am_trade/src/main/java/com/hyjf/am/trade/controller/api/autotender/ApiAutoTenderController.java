/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.autotender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.resquest.api.AutoTenderComboRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.service.api.autotender.ApiAutoTenderService;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;

/**
 * @author libin
 * 第三方投资散标
 * @version ApiAutoTenderController.java, v0.1 2018年8月27日 上午10:03:39
 */
@RestController
@RequestMapping("/am-trade/autotender")
public class ApiAutoTenderController extends BaseController{
	
    @Autowired
    private ApiAutoTenderService apiAutoTenderService;
    
	/**
	 * 
	 * 投资预插入
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
    private Integer updateTenderLog(@RequestBody AutoTenderComboRequest autoTenderComboRequest) {
    	Integer flg = apiAutoTenderService.updateTenderLog(autoTenderComboRequest);
        return flg;
    }
}
