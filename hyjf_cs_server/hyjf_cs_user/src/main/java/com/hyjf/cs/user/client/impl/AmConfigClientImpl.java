package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.response.config.*;
import com.hyjf.am.response.trade.BankInterfaceResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.user.NewAppQuestionCustomizeResponse;
import com.hyjf.am.response.user.QuestionCustomizeResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmConfigClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author xiasq
 * @version AmConfigClientImpl, v0.1 2018/4/23 20:01
 */
@Cilent
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
    public List<NewAppQuestionCustomizeVO> getNewAppQuestionList() {
        NewAppQuestionCustomizeResponse response = restTemplate
                .getForEntity(configService+"/config/getNewAppQuestionList", NewAppQuestionCustomizeResponse.class).getBody();
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

    /**
     * 通过网站设置获取公司信息
     *
     * @return
     */
    @Override
    public SiteSettingsVO selectSiteSetting() {
        String url = "http://AM-CONFIG/am-config/site_settings/select_site_setting";
        SiteSettingsResponse response = restTemplate.getForEntity(url,SiteSettingsResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
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
    public JxBankConfigVO getJxBankConfigByBankId(String bankId) {
        JxBankConfigResponse response = restTemplate
                .getForEntity(configService+"/config/getJxBankConfigByBankId/" + bankId, JxBankConfigResponse.class).getBody();
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
                    .getForEntity(configService+"/appversion/getUpdateversion/" + type+"/"+isupdate+"/"+versionStr, VersionConfigBeanResponse.class).getBody();
        return response.getResult();
    }

    /**
     * 根据银行code查询银行配置
     * @auth sunpeikai
     * @param code 银行code,例如：招商银行,code是CMB
     * @return
     */
    @Override
    public BankConfigVO getBankConfigByCode(String code) {
        String url = configService+"/config/getBankConfigByCode/" + code;
        BankConfigResponse response = restTemplate
                .getForEntity(url, BankConfigResponse.class).getBody();
        return response.getResult();
    }

    @Override
    public Integer getBankInterfaceFlagByType(String type) {
        BankInterfaceResponse response = restTemplate
                .getForEntity(configService+"/bankInterface/getBankInterfaceFlagByType/" + type, BankInterfaceResponse.class).getBody();
        if (response != null) {
            return response.getFlag();
        }
        return null;
    }

    /**
     * 根据设备唯一标识获取用户角标
     * @auth sunpeikai
     * @param sign 设备唯一标识
     * @return
     */
    @Override
    public UserCornerVO getUserCornerBySign(String sign) {
        UserCornerResponse response = restTemplate
                .getForEntity(configService+"/userCorner/getUserCornerBySign/" + sign, UserCornerResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新用户角标数据
     * @auth sunpeikai
     * @param userCornerVO 用户角标数据
     * @return
     */
    @Override
    public Integer updateUserCorner(UserCornerVO userCornerVO) {
        UserCornerResponse response = restTemplate
                .postForEntity(configService+"/userCorner/updateUserCorner",userCornerVO, UserCornerResponse.class).getBody();
        if (response != null) {
            return response.getSuccessCount();
        }
        return 0;
    }

    /**
     * 插入一条新的用户角标数据
     * @auth sunpeikai
     * @param userCornerVO 用户角标数据
     * @return
     */
    @Override
    public Integer insertUserCorner(UserCornerVO userCornerVO) {
        UserCornerResponse response = restTemplate
                .postForEntity(configService+"/userCorner/insertUserCorner", userCornerVO, UserCornerResponse.class).getBody();
        if (response != null) {
            return response.getSuccessCount();
        }
        return 0;
    }

    /**
     * 根据银行id查询江西银行配置
     * @auth sunpeikai
     * @param id 银行id
     * @return
     */
    @Override
    public JxBankConfigVO getJxBankConfigById(Integer id) {
        JxBankConfigResponse response = restTemplate
                .getForEntity(configService+"/config/getJxBankConfigByBankId/" + id, JxBankConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<MessagePushTemplateVO> searchList(MsgPushTemplateRequest request) {
        MessagePushTemplateResponse response = restTemplate.postForObject(
                "http://AM-CONFIG/am-config/messagePushTemplate/searchList",request ,MessagePushTemplateResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取客组配置List
     *
     * @return
     */
    @Override
    public List<CustomerServiceGroupConfigVO> selectCustomerServiceGroupConfigList() {
        CustomerServiceGroupConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/customerServiceGroupConfig/selectCustomerServiceGroupConfigList", CustomerServiceGroupConfigResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取坐席配置List
     *
     * @return
     */
    @Override
    public List<CustomerServiceRepresentiveConfigVO> selectCustomerServiceRepresentiveConfig() {
        CustomerServiceRepresentiveConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/customerServiceRepresentiveConfig/selectCustomerServiceGroupConfigList", CustomerServiceRepresentiveConfigResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }


    /**
     * 根据sourceId查询该渠道是否被禁用
     *
     * @param sourceId
     * @return
     */
    @Override
    public CustomerServiceChannelVO selectCustomerServiceChannelBySourceId(Integer sourceId) {
        CustomerServiceChannelResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/customerServiceChannel/selectCustomerServiceChannelBySourceId", CustomerServiceChannelResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据当前拥有人姓名查询坐席配置
     *
     * @param currentOwner
     * @return
     */
    @Override
    public CustomerServiceRepresentiveConfigVO selectCustomerServiceRepresentiveConfigByUserName(String currentOwner) {
        CustomerServiceRepresentiveConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/customerServiceRepresentiveConfig/selectCustomerServiceRepresentiveConfigByUserName/"+currentOwner, CustomerServiceRepresentiveConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
