package com.hyjf.cs.market.service.impl;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.vo.config.SubmissionsVO;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version SubmissionServiceImpl, v0.1 2018/8/10 16:53
 */
@Service
public class SubmissionServiceImpl implements SubmissionService {
    @Autowired
    AmConfigClient amConfigClient;

    @Override
    public IntegerResponse addSubmission(SubmissionsVO submissions) {
        return amConfigClient.addSubmission(submissions);
    }
}
