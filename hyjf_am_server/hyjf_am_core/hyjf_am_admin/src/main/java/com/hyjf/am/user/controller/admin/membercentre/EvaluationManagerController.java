/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.membercentre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.EvalationResultResponse;
import com.hyjf.am.response.user.UserEvalationQuestionResponse;
import com.hyjf.am.response.user.UserEvalationResultResponse;
import com.hyjf.am.resquest.user.EvalationRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.UserEvalationResult;
import com.hyjf.am.user.dao.model.customize.EvalationResultCustomize;
import com.hyjf.am.user.dao.model.customize.UserEvalationQuestionCustomize;
import com.hyjf.am.user.service.admin.membercentre.EvaluationManagerService;
import com.hyjf.am.vo.user.EvalationResultVO;
import com.hyjf.am.vo.user.UserEvalationQuestionVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 *          后台会员中心-用户测评
 */

@RestController
@RequestMapping("/am-user/evaluationManager")
public class EvaluationManagerController extends BaseController {
    @Autowired
    private EvaluationManagerService evaluationManagerService;

    /**
     * 根据筛选条件查找(用户测评列表显示)
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectUserEvalationResultList")
    public EvalationResultResponse selectUserEvalationResultList(@RequestBody @Valid EvalationRequest request) {
        logger.info("---selectUserEvalationResultList by param---  " + JSONObject.toJSON(request));
        EvalationResultResponse response = new EvalationResultResponse();
        Map<String,Object> mapParam = paramSet(request);
        int usesrCount = evaluationManagerService.countEvalationResultRecord(mapParam);
        Paginator paginator = new Paginator(request.getCurrPage(), usesrCount,request.getPageSize());
        if(request.getPageSize()==0){
            paginator = new Paginator(request.getCurrPage(), usesrCount);
        }
        response.setCount(usesrCount);
        if(usesrCount>0){
            List<EvalationResultCustomize> userManagerCustomizeList = evaluationManagerService.selectUserEvalationResultList(mapParam,paginator.getOffset(), paginator.getLimit());
            if (!CollectionUtils.isEmpty(userManagerCustomizeList)) {
                List<EvalationResultVO> userVoList = CommonUtils.convertBeanList(userManagerCustomizeList, EvalationResultVO.class);
                response.setResultList(userVoList);
                response.setRtn(Response.SUCCESS);
                response.setMessage(Response.SUCCESS_MSG);

            }
        }
        return response;
    }

    @RequestMapping("/selectEvaluationDetailById/{userId}")
    public UserEvalationResultResponse selectEvaluationDetailById(@PathVariable String userId) {
        logger.info("---selectEvaluationDetailById ---  " + JSONObject.toJSON(userId));
        UserEvalationResultResponse response = new UserEvalationResultResponse();
        String returnCode = Response.FAIL;
        if(StringUtils.isNotBlank(userId)){
            int userIdInt = Integer.parseInt(userId);
            UserEvalationResult userEvalationResult = evaluationManagerService.selectUserEvalationResultByUserId(userIdInt);
            if(null!=userEvalationResult){
                UserEvalationResultVO userManagerDetailVO = new UserEvalationResultVO();
                BeanUtils.copyProperties(userEvalationResult, userManagerDetailVO);
                response.setResult(userManagerDetailVO);
                returnCode = Response.SUCCESS;
            }
        }
        response.setRtn(returnCode);//代表成功
        return response;
    }

    /**
     * 查询条件设置
     *
     * @param userRequest
     * @return
     */
    private Map<String, Object> paramSet(EvalationRequest userRequest) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("userName", userRequest.getUserName());
        mapParam.put("realName", userRequest.getRealName());
        mapParam.put("mobile", userRequest.getMobile());
        mapParam.put("evaluationType", userRequest.getEvaluationType());
        mapParam.put("accountStatus", userRequest.getAccountStatus());
        mapParam.put("evaluationStatus", userRequest.getEvaluationStatus());
        mapParam.put("userProperty", userRequest.getUserProperty());
        return mapParam;
    }

    @RequestMapping("/getUserQuestionInfoById/{evalationId}")
    public UserEvalationQuestionResponse getUserQuestionInfoById(@PathVariable String evalationId) {
        logger.info("---getUserQuestionInfoById ---  " + JSONObject.toJSON(evalationId));
        UserEvalationQuestionResponse response = new UserEvalationQuestionResponse();
        String returnCode = Response.FAIL;
        if(StringUtils.isNotBlank(evalationId)){
            int userIdInt = Integer.parseInt(evalationId);
            List<UserEvalationQuestionCustomize> listQuestion = evaluationManagerService.getUserQuestionInfoById(userIdInt);
            if(null!=listQuestion&&listQuestion.size()>0){
                List<UserEvalationQuestionVO> userManagerDetailVO  = CommonUtils.convertBeanList(listQuestion, UserEvalationQuestionVO.class);
                response.setResultList(userManagerDetailVO);
                returnCode = Response.SUCCESS;
            }
        }
        response.setRtn(returnCode);//代表成功
        return response;
    }
}
