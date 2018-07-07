/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.client.impl;

import com.hyjf.callcenter.client.AmDataCollectClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangjun
 * @version AmDataCollectClientImpl, v0.1 2018/7/6 17:15
 */
@Service
public class  AmDataCollectClientImpl  implements AmDataCollectClient {
    private static Logger logger = LoggerFactory.getLogger(AmDataCollectClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;

}
