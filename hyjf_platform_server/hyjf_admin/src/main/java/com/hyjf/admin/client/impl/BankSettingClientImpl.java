/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.BankSettingRequestBean;
import com.hyjf.admin.client.BankSettingClient;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.response.admin.AdminBankSettingResponse;
import com.hyjf.am.response.admin.AdminFeeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author dangzw
 * @version BankSettingClientImpl, v0.1 2018/7/24 22:57
 */
@Service
public class BankSettingClientImpl implements BankSettingClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     *（条件）列表查询
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse selectBankSettingList(AdminBankSettingRequest request) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/banksetting/list",
                request, AdminBankSettingResponse.class);
    }

    /**
     * 画面迁移(含有id更新，不含有id添加)
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse getRecord(AdminBankSettingRequest request) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/banksetting/info",
                request, AdminBankSettingResponse.class);
    }

    /**
     * （条件）数据查询
     * @param bank
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<JxBankConfigVO> getRecordList(JxBankConfigVO bank, int limitStart, int limitEnd) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/banksetting/searchForInsert",
                bank, List.class);
    }

    /**
     * 添加
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse insertRecord(AdminBankSettingRequest request) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/banksetting/insert",
                request, AdminBankSettingResponse.class);
    }
}
