/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.evaluation;

import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.bean.ResultApiBean;
import com.hyjf.cs.user.bean.ThirdPartyEvaluationRequestBean;
import com.hyjf.cs.user.bean.ThirdPartyEvaluationResultBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.evaluation.EvaluationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangqingqing
 * @version EvaluationController, v0.1 2018/7/3 17:26
 */

@Api(value = "api端风险测评接口",tags = "api端-风险测评接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-api/server/user/evaluation")
public class ApiEvaluationController extends BaseUserController {

    @Autowired
    EvaluationService evaluationService;

    /**
     * 用户风险测评
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "风险测评接口")
    @PostMapping(value = "/saveUserEvaluationResults.do")
    public ResultApiBean<ThirdPartyEvaluationResultBean> saveUserEvaluationResults(@RequestBody ThirdPartyEvaluationRequestBean thirdPartyFinancialadvisorRequestBean) {
        ResultApiBean<ThirdPartyEvaluationResultBean> result = new ResultApiBean<>();
        ThirdPartyEvaluationResultBean resultBean = new ThirdPartyEvaluationResultBean();
        //验证请求参数
        EvalationVO evalation = evaluationService.checkParam(thirdPartyFinancialadvisorRequestBean);
        logger.info("用户手机号："+thirdPartyFinancialadvisorRequestBean.getMobile());
        UserVO user = evaluationService.getUsersByMobile(thirdPartyFinancialadvisorRequestBean.getMobile());
        CheckUtil.check(user != null, MsgEnum.STATUS_CE000006);
        int flag = evaluationService.ThirdPartySaveUserEvaluationResults(user, evalation.getScoreDown() - 2, evalation,
                thirdPartyFinancialadvisorRequestBean.getInstCode(), thirdPartyFinancialadvisorRequestBean.getChannel());
        resultBean.setAccountId(thirdPartyFinancialadvisorRequestBean.getAccountId());
        CheckUtil.check(flag != 0, MsgEnum.STATUS_CE999999);
        result.setData(resultBean);
        return result;
    }
}
