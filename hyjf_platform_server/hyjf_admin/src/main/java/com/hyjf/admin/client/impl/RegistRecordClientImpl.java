/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.RegistRecordClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.RegistRecordResponse;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.vo.user.RegistRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author nixiaoling
 * @version UserCenterClientImpl, v0.1 2018/6/20 15:38
 */
@Service
public class RegistRecordClientImpl implements RegistRecordClient {
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 查找注册记录列表
     *
     * @param request
     * @return
     */
    @Override
    public List<RegistRecordVO> findRegistRecordList(RegistRcordRequest request) {
        RegistRecordResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/registRecord/registRecordList", request, RegistRecordResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }
}
