/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.response.EvalationInitResponseBean;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.EvalationService;
import com.hyjf.am.response.user.EvalationResultResponse;
import com.hyjf.am.resquest.user.EvalationRequest;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version EvaluationServiceImpl, v0.1 2018/6/25 17:24
 */
@Service
public class EvalationServiceImpl implements EvalationService {

    @Autowired
    private AmUserClient evalationClient;
    /**
     * 查找用户测评数据
     * @return
     */
    @Override
    public EvalationResultResponse selectUserEvalationResultList(EvalationRequest request){
        return  evalationClient.selectUserEvalationResultList(request);
    }

    /**
     * 查找用户测评结果
     * @param userId
     * @return
     */
    @Override
    public UserEvalationResultVO selectUserEvalationResultByUserId(String userId){
        return evalationClient.selectEvaluationDetailById(userId);
    }

    /**
     * 用户测评初始化
     * @return
     */
    @Override
    public EvalationInitResponseBean initUserManaget(){
        EvalationInitResponseBean evalationInitResponseBean = new EvalationInitResponseBean();
        // 开户状态
        Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
        // 测评状态
        List<ParamName> evaluationType = createEvaluationType();
        //测评等级
        List<ParamName> evaluationStatus = createEvaluationStatus();
        // 注册平台
        Map<String, String> registPlat = CacheUtil.getParamNameMap("CLIENT");

        evalationInitResponseBean.setAccountStatus(accountStatus);
        evalationInitResponseBean.setEvaluationType(evaluationType);
        evalationInitResponseBean.setEvaluationStatus(evaluationStatus);
        return evalationInitResponseBean;
    }
    private List<ParamName> createEvaluationType() {
        List<ParamName> list = new ArrayList<ParamName>();
        ParamName paramName1 = new ParamName();
        paramName1.setNameCd("保守型");
        list.add(paramName1);
        ParamName paramName3 = new ParamName();
        paramName3.setNameCd("稳健型");
        list.add(paramName3);
        ParamName paramName5 = new ParamName();
        paramName5.setNameCd("激进型");
        list.add(paramName5);
        return list;
    }

    private List<ParamName> createEvaluationStatus() {
        List<ParamName> list = new ArrayList<ParamName>();
        ParamName paramName1 = new ParamName();
        paramName1.setNameCd("已测评");
        list.add(paramName1);
        ParamName paramName2 = new ParamName();
        paramName2.setNameCd("未测评");
        list.add(paramName2);
        ParamName paramName3 = new ParamName();
        paramName3.setNameCd("已过期");
        list.add(paramName3);
        return list;
    }
    }
