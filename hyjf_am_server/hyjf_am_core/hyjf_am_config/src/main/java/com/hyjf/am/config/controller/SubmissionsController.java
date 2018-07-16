package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.config.service.SubmissionsService;
import com.hyjf.am.response.config.SubmissionsResponse;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import com.hyjf.am.vo.config.SubmissionsCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.Oneway;
import java.util.List;
import java.util.Map;

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
     * 查询字典表数据
     *
     * @return
     */
    @PostMapping("/getParamList")
    public SubmissionsResponse findParamList() {
        SubmissionsResponse response = new SubmissionsResponse();
        List<ParamName> client = submissionsService.getParamNameList("CLIENT");
        return response;
    }

    /**
     * 查询列表数据
     *
     * @return
     */
    @PostMapping("/getRecordList")
    public SubmissionsResponse findSubmissionsList(@RequestBody SubmissionsRequest form) {
        SubmissionsResponse response = new SubmissionsResponse();
        List<ParamName> client = submissionsService.getParamNameList("CLIENT");
        return response;
    }

    /**
     * 根据查询条件 取得数据
     * @param msb
     * @param limitStart
     * @param limitEnd
     * @return
     * @author Administrator
     */
    @PostMapping("/getExcleList")
    public List<SubmissionsCustomizeVO> queryRecordList(Map<String, Object> msb, int limitStart, int limitEnd) {
        if (limitStart == 0 || limitStart > 0) {
            msb.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            msb.put("limitEnd", limitEnd);
        }
        return submissionsService.queryRecordList(msb);
    }

}
