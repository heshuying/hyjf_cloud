package com.hyjf.admin.client;

import com.hyjf.am.response.config.SubmissionsResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.config.SubmissionsRequest;

/**
 * @author lisheng
 * @version SubmissionsClient, v0.1 2018/7/18 11:44
 */

public interface SubmissionsClient {
    public UserResponse findUserByCondition( String userName);

    public SubmissionsResponse findSubmissionsList(SubmissionsRequest form);

    public UserResponse findUserByUserId(Integer userId);

    public SubmissionsResponse updateSubmissionsStatus(SubmissionsRequest form);

    public SubmissionsResponse exportSubmissionsList(SubmissionsRequest form);
}
