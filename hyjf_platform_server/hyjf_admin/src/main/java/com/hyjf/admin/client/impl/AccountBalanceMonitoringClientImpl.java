package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.AccountBalanceMonitoringRequestBean;
import com.hyjf.admin.client.AccountBalanceMonitoringClient;
import com.hyjf.am.response.admin.AdminAccountBalanceMonitoringResponse;
import com.hyjf.am.resquest.admin.AdminAccountBalanceMonitoringRequest;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
@Service
public class AccountBalanceMonitoringClientImpl implements AccountBalanceMonitoringClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询配置中心平台账户配置 余额监控
     * @param adminRequest
     * @return
     */
    @Override
    public AdminAccountBalanceMonitoringResponse selectAccountBalanceMonitoringByPage(AdminAccountBalanceMonitoringRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/accountbalance/selectAccountBalanceMonitoringByPage";
        AdminAccountBalanceMonitoringResponse response = restTemplate.postForEntity(url,adminRequest,AdminAccountBalanceMonitoringResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response;
        }
        return null;
    }

    /**
     * 编辑画面检索列表
     * @param adminRequest
     * @return
     */
    @Override
    public AdminAccountBalanceMonitoringResponse selectaccountBalanceMonitoringById(AdminAccountBalanceMonitoringRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/accountbalance/selectaccountBalanceMonitoringById";
        AdminAccountBalanceMonitoringResponse response = restTemplate.postForEntity(url,adminRequest,AdminAccountBalanceMonitoringResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response;
        }
        return null;
    }

    /**
     * 查询配置中心平台账户配置 余额监控
     * @param adminRequest
     * @return
     */
    @Override
    public List<AccountBalanceMonitoringRequestBean> searchMerchantAccountList(AdminAccountBalanceMonitoringRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/accountbalance/selectAccountBalanceMonitoringByPage";
        AdminAccountBalanceMonitoringResponse response = restTemplate.postForEntity(url,adminRequest,AdminAccountBalanceMonitoringResponse.class).getBody();
        List<AccountBalanceMonitoringRequestBean> res = new ArrayList<AccountBalanceMonitoringRequestBean>();
        if (Validator.isNotNull(response)){
            List<MerchantAccountVO> merchantAccountVOList=response.getResultList();
            BeanUtils.copyProperties(merchantAccountVOList,res);
            return res;
        }
        return null;
    }
    /**
     * 数据更新 平台账户设置
     * @param adminRequest
     * @return
     */
    @Override
    public AdminAccountBalanceMonitoringResponse updateMerchantAccountList(List<AccountBalanceMonitoringRequestBean> adminRequest){
        String url = "http://AM-TRADE/am-trade/config/accountbalance/updateMerchantAccountList";
        AdminAccountBalanceMonitoringResponse response = restTemplate.postForEntity(url,adminRequest,AdminAccountBalanceMonitoringResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response;
        }
        return null;
    }

}
