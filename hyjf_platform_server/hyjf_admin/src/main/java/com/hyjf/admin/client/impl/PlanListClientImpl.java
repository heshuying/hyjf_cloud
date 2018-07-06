/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.PlanListClient;

/**
 * @author libin
 * @version PlanListClientImpl.java, v0.1 2018年7月6日 上午9:12:10
 */
@Service
public class PlanListClientImpl implements PlanListClient{
    @Autowired
    private RestTemplate restTemplate;
}
