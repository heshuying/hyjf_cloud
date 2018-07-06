package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.vo.config.AdminSystemVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangqingqing
 * @version AmConfigClientImpl, v0.1 2018/4/23 20:01
 */
@Service
public class AmConfigClientImpl implements AmConfigClient {
    private static Logger logger = LoggerFactory.getLogger(AmConfigClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 用loginUserId去am-config查询登录的用户信息
     * @auth sunpeikai
     * @param loginUserId 登录用户id
     * @return
     */
    @Override
    public AdminSystemVO getUserInfoById(Integer loginUserId) {
        String url = "http://AM-CONFIG/am-config/adminSystem/get_admin_system_by_userid/" + loginUserId;
        AdminSystemResponse response = restTemplate.getForEntity(url,AdminSystemResponse.class).getBody();
        if(response != null){
            return response.getResult();
        }
        return null;
    }
}
