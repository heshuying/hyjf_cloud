package com.hyjf.am.config.controller.app.submission;

import com.hyjf.am.config.dao.model.auto.Submissions;
import com.hyjf.am.config.service.SubmissionsService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.vo.config.SubmissionsVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lisheng
 * @version SubmissionController, v0.1 2018/11/23 9:06
 */
@RestController
@RequestMapping("/am-config/submission")
public class SubmissionController {
    @Autowired
    private SubmissionsService submissionsService;
    /**
     * 添加意见反馈
     * @return
     */
    @PostMapping("/addSubmission")
    public IntegerResponse addSubmission(@RequestBody SubmissionsVO form) {
        IntegerResponse response = new IntegerResponse();
        Submissions submissions = CommonUtils.convertBean(form, Submissions.class);
        response.setResultInt(submissionsService.addSubmission(submissions));
        return response;
    }
}
