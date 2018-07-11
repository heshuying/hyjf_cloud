package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.JobMapper;
import com.hyjf.am.config.dao.model.auto.Job;
import com.hyjf.am.config.dao.model.auto.JobExample;
import com.hyjf.am.config.service.RecruitMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisheng
 * @version RecruitMessageServiceImpl, v0.1 2018/7/10 14:28
 */

@Service
public class RecruitMessageServiceImpl implements RecruitMessageService {
    @Autowired
    JobMapper jobMapper;
    @Override
    public List<Job> getJobs() {
        JobExample example = new JobExample();
        JobExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(1);//开启状态
        example.setOrderByClause("`order` Asc,`create_time` Desc");
        return jobMapper.selectByExampleWithBLOBs(example);
    }
}
