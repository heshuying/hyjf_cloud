/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.service.SynParamService;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.BaseVO;

/**
 * @author dxj
 * @version SynParamController.java, v0.1 2018年6月13日 上午11:24:09
 */
@RestController
@RequestMapping("/am-config/sync")
public class SynParamController {
	
    @Autowired
    private SynParamService synParamService;

    
    /**
     *  同步param到redis
     * @return
     */
    @GetMapping("/synParam")
    public Response<BaseVO> synParam(){
    	Response<BaseVO>  response = new Response<BaseVO>();
    	
    	synParamService.synParam();
    	
        return response;
    }
}
