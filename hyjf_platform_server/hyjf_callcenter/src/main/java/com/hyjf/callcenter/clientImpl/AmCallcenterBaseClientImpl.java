/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.clientImpl;

import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.client.AmCallcenterBaseClient;
import com.hyjf.callcenter.serviceImpl.UserServiceImpl;
import com.hyjf.ribbon.EurekaInvokeClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangjun
 * @version AmCallcenterBaseClientImpl, v0.1 2018/6/6 10:03
 */
@Service
public class AmCallcenterBaseClientImpl implements AmCallcenterBaseClient {
    private static final Logger logger = LoggerFactory.getLogger(AmCallcenterBaseClientImpl.class);
    private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();
    @Override
    public UserVO getUser(UserBean bean) {
        String condition = null;
        if(StringUtils.isNotBlank(bean.getUserName())){
            condition = bean.getUserName();
        }
        if(StringUtils.isNotBlank(bean.getMobile())){
            condition = bean.getMobile();
        }
        UserResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/user/findByCondition/" + condition, UserResponse.class)
                .getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
