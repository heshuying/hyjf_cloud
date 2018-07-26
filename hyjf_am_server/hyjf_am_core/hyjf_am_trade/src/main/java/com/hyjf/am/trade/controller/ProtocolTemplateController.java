/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.trade.ProtocolTemplateResponse;
import com.hyjf.am.trade.service.ProtocolTemplateService;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;

import io.swagger.annotations.Api;

/**
 * @author libin
 * @version ProtocolTemplateController.java, v0.1 2018年7月26日 下午5:07:37
 */
@Api(value = "协议模板")
@RestController
@RequestMapping("/am-trade/protocol")
public class ProtocolTemplateController extends BaseController{

    @Autowired
    private ProtocolTemplateService protocolTemplateService;
    
    @GetMapping("/getProtocolTemplateVOByDisplayName/{displayName}")
    public ProtocolTemplateResponse getProtocolTemplateVOByDisplayName(@PathVariable String displayName){
    	ProtocolTemplateResponse response = new ProtocolTemplateResponse();
    	List<ProtocolTemplateVO> list = this.protocolTemplateService.getProtocolTemplateVOByDisplayName(displayName);
    	if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(list);
        }
        return response;
    }
	
}
