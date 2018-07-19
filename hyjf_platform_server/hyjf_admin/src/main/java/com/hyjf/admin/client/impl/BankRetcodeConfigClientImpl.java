package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BankRetcodeConfigClient;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankRetcodeConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author by xiehuili on 2018/7/17.
 */
@Service
public class BankRetcodeConfigClientImpl implements BankRetcodeConfigClient {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 查询返回码配置列表
     * @param adminRequest
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse selectBankRetcodeListByPage(AdminBankRetcodeConfigRequest adminRequest){
        String url = "http://AM-CONFIG/am-config/config/bankretcodeconfig/list";
        BankReturnCodeConfigResponse response = restTemplate.postForEntity(url,adminRequest,BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    /**
     * 查询返回码配置详情页面
     * @param adminRequest
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse selectBankRetcodeConfigInfo(AdminBankRetcodeConfigRequest adminRequest){
        String url = "http://AM-CONFIG/am-config/config/bankretcodeconfig/info";
        BankReturnCodeConfigResponse response = restTemplate.postForEntity(url,adminRequest,BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 编辑保存返回码配置
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse insertBankReturnCodeConfig(AdminBankRetcodeConfigRequest req){
        String url = "http://AM-CONFIG/am-config/config/bankretcodeconfig/insert";
        BankReturnCodeConfigResponse response = restTemplate.postForEntity(url,req,BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 修改返回码配置
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse updateBankReturnCodeConfig(AdminBankRetcodeConfigRequest req){
        String url = "http://AM-CONFIG/am-config/config/bankretcodeconfig/update";
        BankReturnCodeConfigResponse response = restTemplate.postForEntity(url,req,BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据主键判断维护中数据是否存在
     *
     * @return
     */
    @Override
    public boolean isExistsReturnCode(AdminBankRetcodeConfigRequest record){
        String url = "http://AM-CONFIG/am-config/config/bankretcodeconfig/isExistsReturnCode";
        return restTemplate.postForEntity(url,record,boolean.class).getBody();
    }
    /**
     * 根据条件判断该条数据在数据库中是否存在
     * @param adminRequest
     * @return
     */
    @Override
    public boolean isExistsRecord(AdminBankRetcodeConfigRequest adminRequest){
        String url = "http://AM-CONFIG/am-config/config/bankretcodeconfig/isExistsRecord";
        return restTemplate.postForEntity(url,adminRequest,boolean.class).getBody();
    }
}
