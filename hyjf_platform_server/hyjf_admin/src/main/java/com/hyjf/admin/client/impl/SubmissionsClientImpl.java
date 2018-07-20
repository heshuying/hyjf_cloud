package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.SubmissionsClient;
import com.hyjf.am.response.config.SubmissionsResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author lisheng
 * @version SubmissionsClientImpl, v0.1 2018/7/18 11:44
 */
@Service
public class SubmissionsClientImpl implements SubmissionsClient {
    @Autowired
    private RestTemplate restTemplate;


    /**
     * 根据用户名查询id
     * @param userName
     * @return
     */
    @Override
    public UserResponse findUserByCondition(String userName) {
        return restTemplate.getForObject("http://AM-USER/am-user/user/findByCondition/"+userName, UserResponse.class);
    }

    /**
     * 根据用户id查询用户
     * @param userId
     * @return
     */
    @Override
    public UserResponse findUserByUserId(Integer userId) {
        return restTemplate.getForObject("http://AM-USER/am-user/user/findById/"+userId, UserResponse.class);
    }

    /**
     * 查询列表数据
     * @param form
     * @return
     */
    @Override
    public SubmissionsResponse findSubmissionsList(SubmissionsRequest form) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/submission/getRecordList", form,
                SubmissionsResponse.class);
    }

    /**
     * 查询导出数据
     * @param form
     * @return
     */
    @Override
    public SubmissionsResponse exportSubmissionsList(SubmissionsRequest form) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/submission/getExportRecordList", form,
                SubmissionsResponse.class);
    }
    /**
     * 更新状态
     * @param form
     * @return
     */
    @Override
    public SubmissionsResponse updateSubmissionsStatus(SubmissionsRequest form) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/submission/updateSubmissionsStatus", form,
                SubmissionsResponse.class);
    }

}
