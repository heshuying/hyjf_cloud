/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.maintenance;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.service.SynParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dxj
 * @version SynParamController.java, v0.1 2018年6月13日 上午11:24:09
 */
@RestController
@RequestMapping("/am-config/sync")
public class SynParamController extends BaseConfigController {
	
    @Autowired
    private SynParamService synParamService;

    /**
     *  同步param到redis
     * @return
     */
    @GetMapping("/synParam")
    public boolean synParam(){
    	try {
			synParamService.synParam();
		}catch (Exception e){
    		return false;
		}
        return true;
    }
}
