/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hyjf.admin.client.PlanListClient;
import com.hyjf.admin.service.PlanListService;

/**
 * @author libin
 * @version PlanListServiceImpl.java, v0.1 2018年7月6日 上午9:10:05
 */
@Service
public class PlanListServiceImpl implements PlanListService{
	
    @Autowired
    private PlanListClient planListClient;

}
