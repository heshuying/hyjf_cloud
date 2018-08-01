package com.hyjf.am.config.controller.web;

import com.hyjf.am.config.dao.model.auto.Job;
import com.hyjf.am.config.service.RecruitMessageService;
import com.hyjf.am.response.config.RecruitResponse;
import com.hyjf.am.vo.config.JobsVo;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lisheng
 * @version RecruitMessageController, v0.1 2018/7/10 14:27
 */
@Api(tags = "招贤纳士")
@RestController
@RequestMapping("/am-config/job")
public class RecruitMessageController {

    @Autowired
    RecruitMessageService recruitMessageService;
    /**
     * 获取招贤纳士信息
     *
     * @return
     */
    @GetMapping("/getJobs")
    public RecruitResponse getJobs() {
        RecruitResponse response = new RecruitResponse();
        List<Job> jobs = recruitMessageService.getJobs();
        if (!CollectionUtils.isEmpty(jobs)){
            List<JobsVo> result = CommonUtils.convertBeanList(jobs,JobsVo.class);
            response.setResultList(result);
        }
        return response;
    }



}
