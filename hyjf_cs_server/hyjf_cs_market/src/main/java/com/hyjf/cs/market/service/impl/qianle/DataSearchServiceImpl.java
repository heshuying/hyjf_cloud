package com.hyjf.cs.market.service.impl.qianle;

import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.service.qianle.DataSearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author lisheng
 * @version DataSearchServiceImpl, v0.1 2018/8/21 9:39
 */
@Service
public class DataSearchServiceImpl implements DataSearchService {


    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AmConfigClient amConfigClient;
    @Autowired
    AmTradeClient amTradeClient;
    @Value("${qianle.mobile}")
    private String  configMobile;


    @Override
    public DataSearchCustomizeResponse findDataList(DataSearchRequest dataSearchRequest) {
        DataSearchCustomizeResponse dataSearchCustomizeResponse = new DataSearchCustomizeResponse();
        List<Integer> qianleUser = amUserClient.getQianleUser();
        if (CollectionUtils.isEmpty(qianleUser)) {
            return dataSearchCustomizeResponse;
        }
        dataSearchRequest.setUserIds(qianleUser);
        String type = dataSearchRequest.getType();
        if (StringUtils.equals(type,"1")) {
            dataSearchCustomizeResponse= amTradeClient.queryQianleList(dataSearchRequest);
        }else  if (StringUtils.equals(type,"2")) {
            dataSearchCustomizeResponse = amTradeClient.queryPlanList(dataSearchRequest);
        }else  if (StringUtils.equals(type,"3")) {
             dataSearchCustomizeResponse = amTradeClient.querySanList(dataSearchRequest);
        }
        return dataSearchCustomizeResponse;
    }


    @Override
    public DataSearchCustomizeResponse findExportDataList(DataSearchRequest dataSearchRequest) {
        DataSearchCustomizeResponse dataSearchCustomizeResponse = new DataSearchCustomizeResponse();
        List<Integer> qianleUser = amUserClient.getQianleUser();
        if (CollectionUtils.isEmpty(qianleUser)) {
            return dataSearchCustomizeResponse;
        }
        dataSearchRequest.setUserIds(qianleUser);
        dataSearchCustomizeResponse= amTradeClient.findExportDataList(dataSearchRequest);
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
        return amConfigClient.initSmsConfig(request);
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


