package com.hyjf.admin.service.impl.mobileclient;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.mobileclient.SubmissionsService;
import com.hyjf.am.response.config.SubmissionsResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import com.hyjf.am.vo.config.SubmissionsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version SubmissionsServiceImpl, v0.1 2018/7/13 16:27
 */
@Service
public class SubmissionsServiceImpl implements SubmissionsService {
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AmConfigClient amConfigClient;
    @Autowired
    AmAdminClient amAdminClient;

    @Override
    public UserResponse getUserIdByUserName(String userName) {
        return amUserClient.findUserByCondition(userName);
    }

    @Override
    public UserResponse getUserIdByUserId(Integer userId) {
        return amUserClient.findUserByUserId(userId);
    }

    @Override
    public SubmissionsResponse getSubmissionList(SubmissionsRequest form) {
        return amAdminClient.findSubmissionsList(form);
    }

    @Override
    public SubmissionsResponse updateSubmissionStatus(SubmissionsRequest form) {
        return amAdminClient.updateSubmissionsStatus(form);
    }

    @Override
    public SubmissionsResponse getExportSubmissionList(SubmissionsRequest form) {
        return amAdminClient.exportSubmissionsList(form);
    }

    @Override
    public SubmissionsVO getRecord(SubmissionsRequest request) {
        return amAdminClient.getSubmissionsRecord(request);
    }
}
