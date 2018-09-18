package com.hyjf.admin.service.mobileclient;

import com.hyjf.am.response.config.SubmissionsResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import com.hyjf.am.vo.config.SubmissionsVO;

/**
 * @author lisheng
 * @version SubmissionsService, v0.1 2018/7/13 16:27
 *
 */
public interface SubmissionsService {
    UserResponse getUserIdByUserName(String userName);

    UserResponse getUserIdByUserId(Integer userId);

    SubmissionsResponse getSubmissionList(SubmissionsRequest form);

    SubmissionsResponse updateSubmissionStatus(SubmissionsRequest form);

    SubmissionsResponse getExportSubmissionList(SubmissionsRequest form);

    SubmissionsVO getRecord(SubmissionsRequest request);
}
