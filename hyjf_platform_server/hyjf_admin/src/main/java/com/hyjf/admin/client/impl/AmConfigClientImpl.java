package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.response.config.SiteSettingsResponse;
import com.hyjf.am.response.config.SmsMailTemplateResponse;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.config.SiteSettingsVO;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import com.hyjf.common.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmConfigClientImpl, v0.1 2018/4/23 20:01
 */
@Service
public class AmConfigClientImpl implements AmConfigClient {
    private static Logger logger = LoggerFactory.getLogger(AmConfigClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 用loginUserId去am-config查询登录的用户信息
     * @auth sunpeikai
     * @param loginUserId 登录用户id
     * @return
     */
    @Override
    public AdminSystemVO getUserInfoById(Integer loginUserId) {
        String url = "http://AM-CONFIG/am-config/adminSystem/get_admin_system_by_userid/" + loginUserId;
        AdminSystemResponse response = restTemplate.getForEntity(url,AdminSystemResponse.class).getBody();
        if(response != null){
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<ParamNameVO> getParamNameList(String nameClass) {
        String url = "http://AM-CONFIG/am-config/config/getParamNameList/" + nameClass;
        ParamNameResponse response = restTemplate.getForEntity(url,ParamNameResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询邮件配置
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public SiteSettingsVO findSiteSetting() {

        SiteSettingsResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/siteSettings/findOne/", SiteSettingsResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 查询邮件模板
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public SmsMailTemplateVO findSmsMailTemplateByCode(String mailCode) {
        SmsMailTemplateResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/smsMailTemplate/findSmsMailByCode/" + mailCode,
                        SmsMailTemplateResponse.class)
                .getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据银行错误码查询错误信息
     * @auth sunpeikai
     * @param retCode 错误码
     * @return
     */
    @Override
    public String getBankRetMsg(String retCode) {
        String retMsg = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/adminException/getBankRetMsg/" + retCode, String.class).getBody();
        return retMsg;
    }
}
