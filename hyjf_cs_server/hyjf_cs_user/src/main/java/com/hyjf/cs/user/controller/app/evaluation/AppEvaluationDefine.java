package com.hyjf.cs.user.controller.app.evaluation;

import com.hyjf.cs.user.bean.BaseDefine;

/**
 * Description:投资常量类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: b
 * @version: 1.0
 * Created at: 2015年12月4日 下午2:33:39
 * Modification History:
 * Modified by :
 */


public class AppEvaluationDefine  extends BaseDefine {

    /** @RequestMapping值 */
    public static final String REQUEST_MAPPING = "/financialAdvisor";

    
    /** 初始化调查问卷 */
    public static final String QUESTIONNAIRE_INIT_ACTION = "/questionnaireInit";
    /** 初始化调查问卷 */
    public static final String EVALUATION_RESULT_ACTION = "/evaluationResult";
    
    /** 记录用户行为 */
    public static final String USER_EVALUATION_BEHAVIOR = "/userEvaluationBehavior";
    
    
    /** 开始记录用户行为 */
    public static final String START_USER_EVALUATION_BEHAVIOR = "/startUserEvaluationBehavior";
    
    /** 初始化调查问卷 */
    public static final String INIT_ACTION = "/init";

    public static final String INIT_PATH="/financialAdvisor/evaluation";
    
    /** ValidateForm请求返回值 */
    public static final String JSON_IF_EVALUATION_KEY = "ifEvaluation";
    /** ValidateForm请求返回值*/
    public static final String JSON_USER_EVALATION_RESULT_KEY = "userEvalationResult";
    /** ValidateForm请求返回值*/
    public static final String JSON_QUESRION_LIST_KEY = "questionList";
    /** ValidateForm请求返回值*/
    public static final String JSON_USER_LOGIN_ERROR_KEY = "userError";
    /** ValidateForm请求返回值*/
    public static final String JSON_USER_LOGIN_ERROR_VLUES = "userError";
    
    /** ValidateForm请求返回值*/
    public static final String JSON_VALID_SIGN_KEY = "sign";
}

