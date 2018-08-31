package com.hyjf.cs.market.service.impl;

import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.market.client.AmAdminClient;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.service.DataSearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version DataSearchServiceImpl, v0.1 2018/8/21 9:39
 */
@Service
public class DataSearchServiceImpl implements DataSearchService {

    @Autowired
    AmAdminClient adminClient;

    @Autowired
    AmUserClient amUserClient;
    @Value("${qianle.mobile}")
    private String  configMobile;


    /**
     * 查詢千乐列表数据
     * @param dataSearchRequest
     * @return
     */
    @Override
    public DataSearchCustomizeResponse findDataList(DataSearchRequest dataSearchRequest) {
        DataSearchCustomizeResponse dataSearchCustomizeResponse = new DataSearchCustomizeResponse();
        String type = dataSearchRequest.getType();
        if (StringUtils.equals(type,"1")) {
            dataSearchCustomizeResponse= adminClient.queryQianleList(dataSearchRequest);
        }else  if (StringUtils.equals(type,"2")) {
            dataSearchCustomizeResponse = adminClient.queryPlanList(dataSearchRequest);
        }else  if (StringUtils.equals(type,"3")) {
             dataSearchCustomizeResponse = adminClient.querySanList(dataSearchRequest);
        }
        return dataSearchCustomizeResponse;
    }


    /**
     * 验证手机号
     * @param mobile
     * @return
     */
    @Override
    public boolean checkMobile(String mobile)
    {
        if(StringUtils.isNotBlank(configMobile)){
            String[] split = configMobile.split(",");
            for (String s : split) {
                if (StringUtils.equals(s,mobile)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public SmsConfigResponse initSmsConfig() {
        SmsConfigRequest request = new SmsConfigRequest();
        return adminClient.initSmsConfig(request);
    }

    @Override
    public void saveSmsCode(String checkCode, String mobile, String platform) {
        amUserClient.saveSmsCode(mobile, checkCode, CustomConstants.PARAM_TPL_ZHAOHUIMIMA, CommonConstant.CKCODE_NEW, CustomConstants.CLIENT_WECHAT);

    }

    @Override
    public int checkMobileCode(String phone, String code) {
        return amUserClient.onlyCheckMobileCode(phone,code);
    }


}


