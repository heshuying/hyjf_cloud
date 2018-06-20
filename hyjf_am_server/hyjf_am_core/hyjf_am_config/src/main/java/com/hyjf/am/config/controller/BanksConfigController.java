package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.BankConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfigExample;
import com.hyjf.am.config.dao.model.customize.QuestionCustomize;
import com.hyjf.am.config.service.BankConfigService;
import com.hyjf.am.config.service.QuestionService;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.response.user.QuestionCustomizeResponse;
import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/am-config/config")
public class BanksConfigController {

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
    public int countScore(AnswerRequest answerList) {
        int countScore = questionService.countScore(answerList.getResultList());
        return countScore;
    }
}
