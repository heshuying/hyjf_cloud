package com.hyjf.batch.job.service.impl;

import com.hyjf.batch.job.service.ActivityEndService;
import com.hyjf.batch.job.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version ActivityEndServiceImpl, v0.1 2018/4/19 20:55
 */
@Service
public class ActivityEndServiceImpl extends BaseService implements ActivityEndService {
    @Override
    public void execute() {
        String url = "http://AM-MARKET/activity/batch/update";
        restTemplate.getForEntity(url, String.class);
    }
}
