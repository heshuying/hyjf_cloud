/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.response.EvalationInitResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.EvalationService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.response.config.AnswerResponse;
import com.hyjf.am.response.config.QuestionResponse;
import com.hyjf.am.response.user.EvalationResultResponse;
import com.hyjf.am.resquest.user.EvalationRequest;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.am.vo.config.AnswerVO;
import com.hyjf.am.vo.config.QuestionVO;
import com.hyjf.am.vo.user.UserEvalationQuestionVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.CacheUtil;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 查找用户测评数据
     *
     * @return
     */
    @Override
    public EvalationResultResponse selectUserEvalationResultList(EvalationRequest request) {
        return evalationClient.selectUserEvalationResultList(request);
    }

    /**
     * 查找用户测评结果
     *
     * @param userId
     * @return
     */
    @Override
    public UserEvalationResultVO selectUserEvalationResultByUserId(String userId) {
        return evalationClient.selectEvaluationDetailById(userId);
    }

    /**
     * 根据用户id查找用户信息
     *
     * @param strUserId
     * @return
     */
    @Override
    public UserVO getUserVOByUserId(String strUserId) {
        UserVO userVO = new UserVO();
        if (StringUtils.isNotBlank(strUserId)) {
            int userId = Integer.parseInt(strUserId);
            userVO = evalationClient.findUserById(userId);
        }
        return userVO;
    }

    /**
     * 用户测评初始化
     *
     * @return
     */
    @Override
    public EvalationInitResponseBean initUserManaget() {
        EvalationInitResponseBean evalationInitResponseBean = new EvalationInitResponseBean();
        // 开户状态
        Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
        List<DropDownVO> listtAccountStatus = ConvertUtils.convertParamMapToDropDown(accountStatus);
        evalationInitResponseBean.setAccountStatus(listtAccountStatus);
        // 测评状态
        List<ParamName> evaluationType = createEvaluationType();
        List<DropDownVO> listEvaluationType = ConvertUtils.convertListToDropDown(evaluationType,"nameCd","name");
        //测评等级
        List<ParamName> evaluationStatus = createEvaluationStatus();
        List<DropDownVO> listEvaluationStatus = ConvertUtils.convertListToDropDown(evaluationStatus,"nameCd","name");
        // 注册平台
        Map<String, String> registPlat = CacheUtil.getParamNameMap("CLIENT");
        List<DropDownVO> listRegistPlat = ConvertUtils.convertParamMapToDropDown(registPlat);

        evalationInitResponseBean.setEvaluationType(listEvaluationType);
        evalationInitResponseBean.setEvaluationStatus(listEvaluationStatus);
        return evalationInitResponseBean;
    }

    private List<ParamName> createEvaluationType() {
        List<ParamName> list = new ArrayList<ParamName>();
        ParamName paramName1 = new ParamName();
        paramName1.setNameCd("保守型");
        paramName1.setName("保守型");
        list.add(paramName1);
        ParamName paramName3 = new ParamName();
        paramName3.setNameCd("稳健型");
        paramName3.setName("稳健型");
        list.add(paramName3);
        ParamName paramName5 = new ParamName();
        paramName5.setNameCd("成长型");
        paramName5.setName("成长型");
        list.add(paramName5);
        ParamName paramName6 = new ParamName();
        paramName6.setNameCd("进取型");
        paramName6.setName("进取型");
        list.add(paramName5);
        return list;
    }

    private List<ParamName> createEvaluationStatus() {
        List<ParamName> list = new ArrayList<ParamName>();
        ParamName paramName1 = new ParamName();
        paramName1.setNameCd("已测评");
        paramName1.setName("已测评");
        list.add(paramName1);
        ParamName paramName2 = new ParamName();
        paramName2.setNameCd("未测评");
        paramName2.setName("未测评");
        list.add(paramName2);
        ParamName paramName3 = new ParamName();
        paramName3.setNameCd("已过期");
        paramName3.setName("已过期");
        list.add(paramName3);
        return list;
    }
    /**
     * 根据id查找用户测评的问题与答案
     * @param evalationId
     * @return
     */
    @Override
    public List<UserEvalationQuestionVO> getUserQuestionInfoById(int evalationId){
        List<UserEvalationQuestionVO> userEvalationQuestionVOList = evalationClient.getUserQuestionInfoById(evalationId);
        QuestionResponse questionResponse = amConfigClient.getAllQuestion();
        List<QuestionVO> questionVOList = new ArrayList<QuestionVO>();
        if(null!=questionResponse){
            questionVOList = questionResponse.getResultList();
        }
        AnswerResponse answerResponse = amConfigClient.getAllAnswer();
        List<AnswerVO> answerVOArrayList = new ArrayList<AnswerVO>();
        if(null!=answerResponse){
            answerVOArrayList = answerResponse.getResultList();
        }
        if(null!=userEvalationQuestionVOList&&userEvalationQuestionVOList.size()>0){
            if(null!=questionVOList&&questionVOList.size()>0){
                for(UserEvalationQuestionVO userEvalationQuestionVO:userEvalationQuestionVOList){
                    setQuestionValue(userEvalationQuestionVO,questionVOList);
                }
            }
            if(null!=answerVOArrayList&&answerVOArrayList.size()>0){
                for(UserEvalationQuestionVO userEvalationQuestionVO:userEvalationQuestionVOList){
                    setAnswerValue(userEvalationQuestionVO,answerVOArrayList);
                }
            }
            return userEvalationQuestionVOList;
        }
        return null;
    }
    private void setQuestionValue(UserEvalationQuestionVO userEvalationQuestionVO,List<QuestionVO> questionVOList){
        for(QuestionVO questionVO:questionVOList){
            if(userEvalationQuestionVO.getQuestion().equals(questionVO.getId().toString())){
                userEvalationQuestionVO.setQuestion(questionVO.getQuestion());
            }
        }
    }
    private void setAnswerValue(UserEvalationQuestionVO userEvalationQuestionVO,List<AnswerVO> answerVOArrayList){
        for(AnswerVO answerVO:answerVOArrayList){
            if(userEvalationQuestionVO.getAnswer().equals(answerVO.getId().toString())){
                userEvalationQuestionVO.setAnswer(answerVO.getAnswer());
                userEvalationQuestionVO.setScore(answerVO.getScore().toString());
            }
        }
    }
}
