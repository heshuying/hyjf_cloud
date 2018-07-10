/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.hyjf.admin.beans.request.EvalationRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.EvalationService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.EvalationResponse;
import com.hyjf.am.resquest.user.EvalationRequest;
import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author nxl
 * @version EvaluationController, v0.1 2018/6/25 17:23
 */
@Api(value = "用户测评")
@RestController
@RequestMapping("/hyjf-admin/evaluation")
public class EvalationController extends BaseController {
    @Autowired
    private EvalationService evalationService;

    @ApiOperation(value = "用户测评", notes = "用户测评列表显示")
    @PostMapping(value = "/evalationRecord")
    @ResponseBody
    public AdminResult<ListResult<EvalationVO>> getUserEvaluation(HttpServletRequest request, HttpServletResponse response, @RequestBody EvalationRequestBean evalationRequestBean){
        EvalationRequest evalationRequest = new EvalationRequest();
        BeanUtils.copyProperties(evalationRequest,evalationRequestBean);
        EvalationResponse evalationResponse = evalationService.selectUserEvalationResultList(evalationRequest);
        String status = Response.FAIL;
        if(evalationResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(evalationResponse)) {
            return new AdminResult<>(FAIL, evalationResponse.getMessage());

        }
        return new AdminResult<ListResult<EvalationVO>>(ListResult.build(evalationResponse.getResultList(), evalationResponse.getCount())) ;
    }
    private EvalationRequest serParamRequest(Map<String, Object> mapParam) {
        EvalationRequest request = new EvalationRequest();
        if (null != mapParam && mapParam.size() > 0) {
            if (mapParam.containsKey("userName")) {
                request.setUserName(mapParam.get("userName").toString());
            }
            if (mapParam.containsKey("realName")) {
                request.setRealName(mapParam.get("realName").toString());
            }
            if (mapParam.containsKey("mobile")) {
                request.setMobile(mapParam.get("mobile").toString());
            }
            if (mapParam.containsKey("userProperty")) {
                request.setUserProperty(mapParam.get("userProperty").toString());
            }
            if (mapParam.containsKey("accountStatus")) {
                request.setAccountStatus(mapParam.get("accountStatus").toString());
            }
            if (mapParam.containsKey("evaluationStatus")) {
                request.setEvaluationStatus(mapParam.get("evaluationStatus").toString());
            }
            if (mapParam.containsKey("evaluationType")) {
                request.setEvaluationType(mapParam.get("evaluationType").toString());
            }
            if (mapParam.containsKey("limit")&& StringUtils.isNotBlank(mapParam.get("limit").toString())) {
                request.setLimit((Integer)mapParam.get("limit"));
            }
        }
        return request;
    }

    @ApiOperation(value = "用户测评", notes = "用户测评结果显示")
    @PostMapping(value = "/selectEvaluationDetailById")
    @ResponseBody
    public AdminResult<UserEvalationResultVO> selectEvaluationDetailById(HttpServletRequest request, HttpServletResponse response,@RequestBody String  userId){
        UserEvalationResultVO userEvalationResultVO = evalationService.selectUserEvalationResultByUserId(userId);
        if (null != userEvalationResultVO) {
            return new AdminResult<UserEvalationResultVO>(userEvalationResultVO);
        }
        return new AdminResult<>(FAIL, FAIL_DESC);
    }

}
