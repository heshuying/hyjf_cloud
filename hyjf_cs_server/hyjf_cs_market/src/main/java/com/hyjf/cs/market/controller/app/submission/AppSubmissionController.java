package com.hyjf.cs.market.controller.app.submission;

/**
 * @author lisheng
 * @version AppSubmissionController, v0.1 2018/8/10 16:06
 * 意见反馈
 */
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.SubmissionsVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.market.service.SubmissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Api(tags = "app端-意见反馈")
@RestController
@RequestMapping("/hyjf-app/submission")
public class AppSubmissionController {
    Logger logger = LoggerFactory.getLogger(getClass());
    public static final String RETURN_REQUEST = "/hyjf-app/submission/addSubmission";

    @Autowired
    SubmissionService submissionService;

    /**
     * 查询列表
     * @return
     */
    @ApiOperation(value = "添加意见反馈", notes = "添加意见反馈")
    @PostMapping("/addSubmission")
    public JSONObject addSubmission(HttpServletRequest request,
                                    @RequestHeader(value = "userId") Integer userId,
                                    @RequestHeader(value = "platform") String platform,
                                    @RequestHeader(value = "version") String version) {
        JSONObject ret = new JSONObject();
        ret.put("request", RETURN_REQUEST);
        //意见反馈内容
        String content = request.getParameter("content");
        //手机型号
        String phoneType = request.getParameter("phoneType");
        //手机系统
        String systemVersion= request.getParameter("systemType");

        // 检查参数正确性
        if (Validator.isNull(content)){
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }

        SubmissionsVO submissions= new SubmissionsVO();
        submissions.setUserId(userId);
        submissions.setCreateTime(new Date());
        submissions.setContent(content);
        submissions.setTitle(platform+"意见反馈");
        submissions.setProblem(platform);
        submissions.setState(0);//状态 0未审 1已审核
        submissions.setSysType(Integer.valueOf(platform));// 系统类别 0：PC，1：微官网，2：Android，3：IOS，4：其他
        submissions.setPlatformVersion(version);
        submissions.setPhoneType(phoneType);//手机型号
        submissions.setSysVersion(systemVersion);//操作系统版本号
        int result= this.submissionService.addSubmission(submissions);
        if(result==1){
            ret.put("status", "0");
            ret.put("statusDesc", "成功");
        }else{
            ret.put("status", "2");
            ret.put("statusDesc", "提交意见反馈出现异常，请重新操作！");
        }
        return ret;

    }
}
