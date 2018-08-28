package com.hyjf.cs.market.service;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.vo.config.SubmissionsVO;

/**
 * @author lisheng
 * @version SubmissionService, v0.1 2018/8/10 16:53
 */

public interface SubmissionService {
    /**
     * 添加意见反馈
     * @return
     */
    IntegerResponse addSubmission(SubmissionsVO submissions);

}
