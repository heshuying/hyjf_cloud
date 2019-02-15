package com.hyjf.cs.user.controller.api.aems.evaluation;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.AemsEvaluationRequestBean;
import com.hyjf.cs.user.bean.AemsEvaluationResultBean;
import com.hyjf.cs.user.bean.ResultApiBean;
import com.hyjf.cs.user.service.aems.evalation.AemsEvaluationService;
import com.hyjf.cs.user.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(value = "api端-AEMS风险测评接口",tags = "api端-AEMS风险测评接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-api/aems/evaluation")
public class AemsEvaluationController extends BaseController {


	@Autowired
    AemsEvaluationService evaluationService;
	/**
     * 用户风险测评
     * @param aemsEvaluationRequestBean
     * @return
     */
    @ApiOperation(value = "AEMS风险测评接口", notes = "AEMS风险测评接口")
    @PostMapping(value = "/saveUserEvaluationResults.do", produces = "application/json; charset=utf-8")
    public ResultApiBean<AemsEvaluationResultBean> saveUserEvaluationResults(@RequestBody AemsEvaluationRequestBean aemsEvaluationRequestBean) {
        AemsEvaluationResultBean resultBean=new AemsEvaluationResultBean();
        logger.info(aemsEvaluationRequestBean.getAccountId()+"用户风险测评开始-----------------------------");
        logger.info("第三方请求参数："+JSONObject.toJSONString(aemsEvaluationRequestBean));
        //验证请求参数
        // 验证
        CheckUtil.check(Validator.isNotNull(aemsEvaluationRequestBean.getTimestamp()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(aemsEvaluationRequestBean.getInstCode()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(aemsEvaluationRequestBean.getChkValue()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(aemsEvaluationRequestBean.getMobile()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(aemsEvaluationRequestBean.getPlatform()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(aemsEvaluationRequestBean.getEvalationType()), MsgEnum.STATUS_CE000001);
        // 验签
        CheckUtil.check(SignUtil.AEMSVerifyRequestSign(aemsEvaluationRequestBean, "/aems/evaluation/saveUserEvaluationResults"),
              MsgEnum.ERR_SIGN);
        //根据userId查询用户信息
        UserVO user = evaluationService.getUsersByMobile(aemsEvaluationRequestBean.getMobile());//用户ID
        if (user == null) {
            CheckUtil.check(false, MsgEnum.STATUS_CE000006);
        }
        //根据平常类型查询平常信息
        EvalationVO evalation = evaluationService.getEvalationByEvalationType(aemsEvaluationRequestBean.getEvalationType());
        if(evalation==null){
            CheckUtil.check(false, MsgEnum.STATUS_EV000001);
        }
        // hyjf-api中调用不到bankService的方法、只能从api-web调用处理后传入
        int flag = evaluationService.aemsSaveUserEvaluationResults(user,evalation.getScoreDown()-2,evalation,
                aemsEvaluationRequestBean.getInstCode(),aemsEvaluationRequestBean.getChannel());
        resultBean.setAccountId(aemsEvaluationRequestBean.getAccountId());
        if(flag==0){
            CheckUtil.check(false, MsgEnum.STATUS_CE999999);
        }
        
        return new ResultApiBean<AemsEvaluationResultBean>(resultBean);
    }
	
}
