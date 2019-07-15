/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.client.impl;

import com.hyjf.am.response.admin.CustomerServerResponse;
import com.hyjf.am.vo.admin.JcCustomerServiceVO;
import com.hyjf.data.client.CsMessageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yaoyong
 * @version CsMessageClientImpl, v0.1 2019/6/24 14:45
 */
@Service
public class CsMessageClientImpl implements CsMessageClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public JcCustomerServiceVO getCustomerService() {
        CustomerServerResponse response = restTemplate.getForEntity("http://CS-MESSAGE/cs-message/customerServer/getCustomerService", CustomerServerResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
