package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.Submissions;
import com.hyjf.am.config.dao.model.customize.SubmissionsWithBLOBs;
import com.hyjf.am.config.service.SubmissionsService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.SubmissionsResponse;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import com.hyjf.am.vo.config.SubmissionsCustomizeVO;
import com.hyjf.am.vo.config.SubmissionsVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lisheng
 * @version SubmissionsController, v0.1 2018/7/13 16:19
 */
@RestController
@RequestMapping("/am-config/submission")
public class SubmissionsController {
    @Autowired
    private SubmissionsService submissionsService;

    /**
     * 查询列表数据
     *
     * @return
     */
    @PostMapping("/getRecordList")
    public SubmissionsResponse findSubmissionsList(@RequestBody SubmissionsRequest form) {
        SubmissionsResponse response = new SubmissionsResponse();
        int count = submissionsService.queryTotal(form);
        if(count>0){
            Paginator paginator = new Paginator(form.getCurrPage(), count,form.getPageSize());
            List<SubmissionsCustomizeVO> recordList = submissionsService.queryRecordList(form,paginator.getOffset(), paginator.getLimit());
            List<SubmissionsCustomizeVO> list = CommonUtils.convertBeanList(recordList, SubmissionsCustomizeVO.class);
            response.setResultList(list);
            response.setRecordTotal(count);
        }
        return response;
    }

    /**
     * 查询导出数据
     *
     * @return
     */
    @PostMapping("/getExportRecordList")
    public SubmissionsResponse exportSubmissionsList(@RequestBody SubmissionsRequest form) {
        SubmissionsResponse response = new SubmissionsResponse();
        List<SubmissionsCustomizeVO> recordList = submissionsService.queryRecordList(form,-1, -1);
        List<SubmissionsCustomizeVO> list = CommonUtils.convertBeanList(recordList, SubmissionsCustomizeVO.class);
        response.setResultList(list);
        return response;
    }
    /**
     * 修改状态
     * @return
     */
    @PostMapping("/updateSubmissionsStatus")
    public SubmissionsResponse updateSubmissionsStatus(@RequestBody SubmissionsRequest form) {
        SubmissionsResponse response = new SubmissionsResponse();
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        String status = form.getStatus();
        String submissionsId = form.getSubmissionsId();
        if(StringUtils.isEmpty(submissionsId)||StringUtils.isEmpty(status)){
            return response;
        }
        SubmissionsWithBLOBs submissions = new SubmissionsWithBLOBs();
        submissions.setId(Integer.valueOf(submissionsId));
        submissions.setState(Integer.valueOf(status));
        if(submissionsService.updateSubmissions(submissions)){
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);

        }
        return response;
    }

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
