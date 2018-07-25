/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.BankSettingRequestBean;
import com.hyjf.admin.client.BankSettingClient;
import com.hyjf.admin.client.FeeConfigClient;
import com.hyjf.admin.service.BankSettingService;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.response.admin.AdminBankSettingResponse;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dangzw
 * @version BankSettingServiceImpl, v0.1 2018/7/24 22:53
 */
@Service
public class BankSettingServiceImpl implements BankSettingService {

    @Autowired
    private BankSettingClient bankSettingClient;

    /**
     *（条件）列表查询
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse selectBankSettingList(AdminBankSettingRequest request) {
        return bankSettingClient.selectBankSettingList(request);
    }

    /**
     * 画面迁移(含有id更新，不含有id添加)
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse getRecord(AdminBankSettingRequest request) {
        return bankSettingClient.getRecord(request);
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
        return bankSettingClient.getRecordList(bank, limitStart, limitEnd);
    }

    /**
     * 添加
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse insertRecord(AdminBankSettingRequest request) {
        return bankSettingClient.insertRecord(request);
    }

}
