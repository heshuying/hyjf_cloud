/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.wbs.client.AmUserClient;

/**
 * @author cui
 * @version AmUserClientImpl, v0.1 2019/4/17 16:57
 */
@Service
public class AmUserClientImpl implements AmUserClient {

    private Logger logger=LoggerFactory.getLogger(getClass());

    @Value("${am.user.service.name}")
    private String userService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserVO findUserById(int userId) {
        String url = userService + "/user/findById/" + userId;
        UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
        if (response != null) {
            if (Response.SUCCESS.equals(response.getRtn())) {
                UserVO userVO =  response.getResult();
                return userVO;
            }
            logger.info("response rtn is : {}", response.getRtn());
        } else {
            logger.info("response is null....");
        }
        return null;
    }
}
