package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.BankConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfigExample;
import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.config.dao.model.customize.QuestionCustomize;
import com.hyjf.am.config.service.BankConfigService;
import com.hyjf.am.config.service.QuestionService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.response.user.QuestionCustomizeResponse;
import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
     * @param bankId
     * @return
     */
    @GetMapping("/getBanksConfigByBankId/{bankId}")
    public BanksConfigResponse getBanksConfigByBankId(@PathVariable Integer bankId){
        BanksConfigResponse response = new BanksConfigResponse();
        BankConfig bankConfig = bankConfigService.getBankConfigByBankId(bankId);
        if(null != bankConfig){
            BanksConfigVO banksConfigVO = new BanksConfigVO();
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

    @RequestMapping("/countScore")
    public int countScore(@RequestBody  AnswerRequest answerList) {
        int countScore = questionService.countScore(answerList.getResultList());
        return countScore;
    }
    /**
     * 获取银行列表
     */
    @RequestMapping("/selectBankConfigList")
    public BanksConfigResponse selectBankConfigList(){
        BanksConfigResponse response=null;
        List<BankConfig> listBankConfig = bankConfigService.selectBankConfigList();
        if(null!=listBankConfig&&listBankConfig.size()>0){
            List<BanksConfigVO> listBanksConfig = CommonUtils.convertBeanList(listBankConfig, BanksConfigVO.class);
            response.setResultList(listBanksConfig);
            //代表成功
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }


    @GetMapping("/getParamNameList/{nameClass}")
    public ParamNameResponse getParamNameList(@PathVariable String nameClass){
        ParamNameResponse response = new ParamNameResponse();
        List<ParamName> paramNameList =bankConfigService.getParamNameList(nameClass);
        if (CollectionUtils.isNotEmpty(paramNameList)){
            response.setResultList(CommonUtils.convertBeanList(paramNameList,ParamNameVO.class));
        }
        return response;
    }

}
