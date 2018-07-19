package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.response.user.QuestionCustomizeResponse;
import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmConfigClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author xiasq
 * @version AmConfigClientImpl, v0.1 2018/4/23 20:01
 */
@Service
public class AmConfigClientImpl implements AmConfigClient {
    private static Logger logger = LoggerFactory.getLogger(AmConfigClientImpl.class);

    @Value("${am.config.service.name}")
    private String configService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SmsConfigVO findSmsConfig() {
        SmsConfigResponse response = restTemplate
                .getForEntity(configService+"/smsConfig/findOne", SmsConfigResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * @Description 根据银行卡号查询bankId
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/5 14:58
     */
    @Override
    public String getBankIdByCardNo(String cardNo) {
        return restTemplate
                .getForEntity(configService+"/config/queryBankIdByCardNo/" + cardNo, String.class).getBody();
    }

    /**
     * @param bankId
     * @Description 根据bankId查询所属银行
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/5 15:13
     */
    @Override
    public BanksConfigVO getBankNameByBankId(String bankId) {
        BanksConfigResponse response = restTemplate
                .getForEntity(configService+"/config/getBanksConfigByBankId/" + bankId, BanksConfigResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<QuestionCustomizeVO> getNewQuestionList() {
        QuestionCustomizeResponse response = restTemplate
                .getForEntity(configService+"/config/getNewQuestionList", QuestionCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int countScore(AnswerRequest answerList) {
        int result = restTemplate
                .postForEntity(configService+"/config/countScore", answerList, Integer.class).getBody();
        return result;
    }


	@Override
	public String getBankRetMsg(String retCode) {
		BankReturnCodeConfigVO vo = this.getBankReturnCodeConfig(retCode);
		if (vo == null) {
			return Response.ERROR_MSG;
		}
		return StringUtils.isNotBlank(vo.getRetMsg()) ? vo.getRetMsg() : Response.ERROR_MSG;
	}


    @Override
    public BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode) {
        BankReturnCodeConfigResponse response = restTemplate
                .getForEntity(configService+"/config/getBankReturnCodeConfig/"+retCode,BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据银行卡号获取bankId
     * @param cardNo
     * @return
     */
    @Override
    public String queryBankIdByCardNo(String cardNo) {
        String result = restTemplate
                .getForEntity(configService+"/config/queryBankIdByCardNo/" + cardNo, String.class).getBody();
        return result;
    }


    /**
     * 获取银行卡配置信息
     * @param bankId
     * @return
     */
    @Override
    public BanksConfigVO getBanksConfigByBankId(String bankId) {
        BanksConfigResponse response = restTemplate
                .getForEntity(configService+"/config/getBanksConfigByBankId/" + bankId, BanksConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<ParamNameVO> getParamNameList(String nameClass) {
        String url = configService+"/config/getParamNameList/" + nameClass;
        ParamNameResponse response = restTemplate.getForEntity(url,ParamNameResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public VersionVO getNewVersionByType(Integer type) {
        VersionConfigBeanResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/appversion/getNewVersionByType/" + type, VersionConfigBeanResponse.class).getBody();
        return response.getResult();
    }

    @Override
    public VersionVO getUpdateversion(Integer type, Integer isupdate, String versionStr) {
        VersionConfigBeanResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/appversion/getUpdateversion/" + type+"/"+isupdate+"/"+versionStr, VersionConfigBeanResponse.class).getBody();
        return response.getResult();
    }
}
