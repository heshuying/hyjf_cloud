/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.hyjf.admin.beans.request.EvalationRequestBean;
import com.hyjf.admin.beans.response.EvalationDetailResponseBean;
import com.hyjf.admin.beans.response.EvalationInitResponseBean;
import com.hyjf.admin.beans.vo.EvalationCustomizeVO;
import com.hyjf.admin.beans.vo.UserEvalationQuestionCustomizeVO;
import com.hyjf.admin.beans.vo.UserEvalationResultCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.EvalationService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.EvalationResultResponse;
import com.hyjf.am.resquest.user.EvalationRequest;
import com.hyjf.am.vo.user.UserEvalationQuestionVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nxl
 * @version EvaluationController, v0.1 2018/6/25 17:23
 */
@Api(value = "会员中心-用户测评",tags = "会员中心-用户测评")
@RestController
@RequestMapping("/hyjf-admin/evaluation")
public class EvalationController extends BaseController {
    @Autowired
    private EvalationService evalationService;

    @ApiOperation(value = "用户测评初始化(下拉列表)", notes = "用户测评页面初始化")
    @PostMapping(value = "/usersInit")
    @ResponseBody
    public  AdminResult<EvalationInitResponseBean>  userManagerInit() {
        EvalationInitResponseBean evalationInitResponseBean =evalationService.initUserManaget();
        return new AdminResult<EvalationInitResponseBean>(evalationInitResponseBean);

    }
    @ApiOperation(value = "用户测评列表查询", notes = "用户测评列表查询")
    @PostMapping(value = "/evalationRecord")
    @ResponseBody
    public AdminResult<ListResult<EvalationCustomizeVO>> getUserEvaluation( @RequestBody EvalationRequestBean evalationRequestBean){
        EvalationRequest evalationRequest = new EvalationRequest();
        BeanUtils.copyProperties(evalationRequestBean,evalationRequest);
        EvalationResultResponse evalationResponse = evalationService.selectUserEvalationResultList(evalationRequest);
        if(evalationResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(evalationResponse)) {
            return new AdminResult<>(FAIL, evalationResponse.getMessage());
        }
        List<EvalationCustomizeVO> evalationCustomizeVOList = new ArrayList<EvalationCustomizeVO>();
        if(null!=evalationResponse.getResultList()&&evalationResponse.getResultList().size()>0){
            evalationCustomizeVOList = CommonUtils.convertBeanList(evalationResponse.getResultList(),EvalationCustomizeVO.class);
        }
        return new AdminResult<ListResult<EvalationCustomizeVO>>(ListResult.build(evalationCustomizeVOList, evalationResponse.getCount())) ;
    }

    @ApiOperation(value = "用户测评结果显示", notes = "用户测评结果显示")
    @PostMapping(value = "/selectEvaluationDetailById")
    @ResponseBody
    public AdminResult<EvalationDetailResponseBean> selectEvaluationDetailById(@RequestBody String  userId){
        UserEvalationResultVO userEvalationResultVO = evalationService.selectUserEvalationResultByUserId(userId);
        EvalationDetailResponseBean evalationDetailResponseBean = new EvalationDetailResponseBean();
        UserVO userVO = evalationService.getUserVOByUserId(userId);
        evalationDetailResponseBean.setIsEvalation("0");
        if(null!=userVO){
            evalationDetailResponseBean.setUserName(userVO.getUsername());
        }
        UserEvalationResultCustomizeVO userEvalationResultCustomizeVO = new UserEvalationResultCustomizeVO();
        if (null != userEvalationResultVO&&userEvalationResultVO.getUserId().toString().equals(userId)) {
            BeanUtils.copyProperties(userEvalationResultVO, userEvalationResultCustomizeVO);
            List<UserEvalationQuestionVO> listUserEvalationQuestionVO =  evalationService.getUserQuestionInfoById(userEvalationResultCustomizeVO.getId());
            List<UserEvalationQuestionCustomizeVO> evalationQuestionCustomizeVOList = new ArrayList<UserEvalationQuestionCustomizeVO>();
            if(null!=listUserEvalationQuestionVO&&listUserEvalationQuestionVO.size()>0){
                evalationQuestionCustomizeVOList = CommonUtils.convertBeanList(listUserEvalationQuestionVO,UserEvalationQuestionCustomizeVO.class);
            }
            evalationDetailResponseBean.setUserEvalationResultCustomizeVO(userEvalationResultCustomizeVO);
            evalationDetailResponseBean.setIsEvalation("1");
            evalationDetailResponseBean.setUserEvalationQuestionCustomizeVO(evalationQuestionCustomizeVOList);
           return new AdminResult<EvalationDetailResponseBean>(evalationDetailResponseBean);
        }
        return new AdminResult<>(FAIL, FAIL_DESC);
    }


}
