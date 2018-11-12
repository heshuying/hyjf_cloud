package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.*;
import com.hyjf.am.config.dao.model.customize.NewAppQuestionCustomize;
import com.hyjf.am.config.dao.model.customize.QuestionCustomize;
import com.hyjf.am.config.service.BankConfigService;
import com.hyjf.am.config.service.QuestionService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.response.config.BankConfigResponse;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.user.NewAppQuestionCustomizeResponse;
import com.hyjf.am.response.user.QuestionCustomizeResponse;
import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.vo.config.NewAppQuestionCustomizeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiasq
 * @version BanksConfigController, v0.1 2018/6/22 14:28
 */
@RestController
@RequestMapping("/am-config/config")
public class BanksConfigController extends BaseConfigController{

    @Autowired
    private BankConfigService bankConfigService;

    @Autowired
    private QuestionService questionService;

    /**
     * 获取银行卡配置信息
     * @param code
     * @return
     */
    @GetMapping("/selectBankConfigByCode/{code}")
    public BankConfigResponse selectBankConfigByCode(@PathVariable String  code){
        BankConfigResponse response = new BankConfigResponse();
        BankConfig bankConfig = bankConfigService.selectBankConfigByCode(code);
        if(null != bankConfig){
            BankConfigVO banksConfigVO = new BankConfigVO();
            BeanUtils.copyProperties(bankConfig,banksConfigVO);
            response.setResult(banksConfigVO);
        }
        return response;
    }


    /**
     *
     * @param retCode
     * @return
     */
    @GetMapping("/getBankReturnCodeConfig/{retCode}")
    public BankReturnCodeConfigResponse getBankReturnCodeConfig(@PathVariable String retCode){
        BankReturnCodeConfigExample example = new BankReturnCodeConfigExample();
        example.createCriteria().andRetCodeEqualTo(retCode);
        BankReturnCodeConfigResponse response = new BankReturnCodeConfigResponse();
        BankReturnCodeConfig retCodes = this.bankConfigService.selectByExample(example);
        if(null != retCodes){
            BankReturnCodeConfigVO bankReturnCodeConfigVO = new BankReturnCodeConfigVO();
            BeanUtils.copyProperties(retCodes,bankReturnCodeConfigVO);
            response.setResult(bankReturnCodeConfigVO);
        }
        return response;
    }
    
    /**
	 * 根据银行卡号获取bankId
	 * @param cardNo
	 * @return
	 */
	@RequestMapping("/queryBankIdByCardNo/{cardNo}")
	public String queryBankIdByCardNo(@PathVariable String cardNo) {
		return bankConfigService.queryBankIdByCardNo(cardNo);
	}

    @RequestMapping("/getNewQuestionList")
    public QuestionCustomizeResponse getNewQuestionList() {
        QuestionCustomizeResponse response = new QuestionCustomizeResponse();
        List<QuestionCustomize> questionCustomizes = questionService.getNewQuestionList();
        if(!CollectionUtils.isEmpty(questionCustomizes)){
            List<QuestionCustomizeVO> questionCustomizeVOS = CommonUtils.convertBeanList(questionCustomizes,QuestionCustomizeVO.class);
            response.setResultList(questionCustomizeVOS);
        }
        return response;
    }

    @RequestMapping("/getNewAppQuestionList")
    public NewAppQuestionCustomizeResponse getNewAppQuestionList() {
        NewAppQuestionCustomizeResponse response = new NewAppQuestionCustomizeResponse();
        List<NewAppQuestionCustomize> questionCustomizes = questionService.getNewAppQuestionList();
        if(!CollectionUtils.isEmpty(questionCustomizes)){
            List<NewAppQuestionCustomizeVO> questionCustomizeVOS = CommonUtils.convertBeanList(questionCustomizes,NewAppQuestionCustomizeVO.class);
            response.setResultList(questionCustomizeVOS);
        }
        return response;
    }

    @RequestMapping("/countScore")
    public int countScore(@RequestBody  AnswerRequest answerList) {
        int countScore = questionService.countScore(answerList.getResultList());
        return countScore;
    }
    /**
     * 获取银行列表
     */
    @RequestMapping("/selectBankConfigList")
    public BankConfigResponse selectBankConfigList(){
        BankConfigResponse response = new BankConfigResponse();
        List<BankConfig> listBankConfig = bankConfigService.selectBankConfigList();
        if(null!=listBankConfig&&listBankConfig.size()>0){
            List<BankConfigVO> listBanksConfig = CommonUtils.convertBeanList(listBankConfig, BankConfigVO.class);
            response.setResultList(listBanksConfig);
            //代表成功
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }


    /**
     * 获取快捷支付银行
     */
    @RequestMapping("/getRechargeQuotaLimit")
    public BankConfigResponse getRechargeQuotaLimit(){
        BankConfigResponse response = new BankConfigResponse();
        List<JxBankConfig> listBankConfig = bankConfigService.getRechargeQuotaLimit(1);
        if(null!=listBankConfig&&listBankConfig.size()>0){
            List<BankConfigVO> listBanksConfig = CommonUtils.convertBeanList(listBankConfig, BankConfigVO.class);
            response.setResultList(listBanksConfig);
        }else{
            response.setResultList(new ArrayList<BankConfigVO>());
        }
        //代表成功
        response.setRtn(Response.SUCCESS);
        return response;
    }

    @GetMapping("/getParamNameList/{nameClass}")
    public ParamNameResponse getParamNameList(@PathVariable String nameClass){
        ParamNameResponse response = new ParamNameResponse();
        List<ParamName> paramNameList =bankConfigService.getParamNameList(nameClass);
        if (CollectionUtils.isNotEmpty(paramNameList)){
            List<ParamNameVO> paramNameVOList = CommonUtils.convertBeanList(paramNameList,ParamNameVO.class);
            response.setResultList(paramNameVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @GetMapping("/getParamName/{other1}")
    public ParamNameResponse getParamName(@PathVariable String other1) {
        ParamNameResponse response = new ParamNameResponse();
        List<ParamName> paramNameList = bankConfigService.getParamName(other1);
        if (CollectionUtils.isNotEmpty(paramNameList)) {
            List<ParamNameVO> paramNameVOList = CommonUtils.convertBeanList(paramNameList,ParamNameVO.class);
            response.setResultList(paramNameVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据银行code获取银行配置
     * @auth sunpeikai
     * @param code 银行code,例如：招商银行,code是CMB
     * @return
     */
    @GetMapping(value = "/getBankConfigByCode/{code}")
    public BankConfigResponse getBankConfigByCode(@PathVariable String code){
        BankConfigResponse response = new BankConfigResponse();
        List<BankConfig> bankConfigList = bankConfigService.getBankConfigByCode(code);
        if(!CollectionUtils.isEmpty(bankConfigList)){
            BankConfigVO bankConfigVO = CommonUtils.convertBean(bankConfigList.get(0),BankConfigVO.class);
            response.setResult(bankConfigVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    /**
     * 获取银行卡配置信息
     * @auth sunpeikai
     * @param bankId 主键id
     * @return
     */
    @GetMapping("/getBankConfigByBankId/{bankId}")
    public BankConfigResponse getBankConfigByBankId(@PathVariable Integer bankId){
        BankConfigResponse response = new BankConfigResponse();
        BankConfig bankConfig = bankConfigService.getBankConfigByBankId(bankId);
        if(null != bankConfig){
            BankConfigVO bankConfigVO = CommonUtils.convertBean(bankConfig,BankConfigVO.class);
            response.setResult(bankConfigVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     *获取银行列表（快捷卡）
     * @return
     */
    @GetMapping("/getbanklist")
    public JxBankConfigResponse getInviteList() {
        JxBankConfigResponse jxBankConfigResponse = new JxBankConfigResponse();
        String methodName = "BanksListServer";
        List<JxBankConfig> bankList = bankConfigService.getBankRecordList(1);
        if(null != bankList){
            List<JxBankConfigVO> jxBankConfigVOS = CommonUtils.convertBeanList(bankList, JxBankConfigVO.class);
            jxBankConfigResponse.setResultList(jxBankConfigVOS);
            jxBankConfigResponse.setRtn(Response.SUCCESS);
        }
        return jxBankConfigResponse;
    }


}
