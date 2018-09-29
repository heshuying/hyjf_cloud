/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.BankSettingService;
import com.hyjf.am.response.admin.AdminBankSettingResponse;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dangzw
 * @version BankSettingServiceImpl, v0.1 2018/7/24 22:53
 */
@Service
public class BankSettingServiceImpl implements BankSettingService {

    @Autowired
    private AmConfigClient amConfigClient;

    @Value("${file.domain.url}")
    private String url;
    @Value("${file.physical.path}")
    private String physical;
    @Value("${file.upload.real.path}")
    private String real;

    /**
     *（条件）列表查询
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse selectBankSettingList(AdminBankSettingRequest request) {
        return amConfigClient.selectBankSettingList(request);
    }

    /**
     * 画面迁移(含有id更新，不含有id添加)
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse getRecord(AdminBankSettingRequest request) {
        return amConfigClient.getRecord(request);
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
        return amConfigClient.getRecordList(bank, limitStart, limitEnd);
    }

    /**
     * 添加
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse insertRecord(AdminBankSettingRequest request) {
        return amConfigClient.insertRecord(request);
    }

    /**
     * 修改 江西银行 银行卡配置
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse updateRecord(AdminBankSettingRequest request) {
        return amConfigClient.updateRecord(request);
    }

    /**
     * 删除 江西银行 银行卡配置
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse deleteRecord(AdminBankSettingRequest request) {
        return amConfigClient.deleteRecord(request);
    }
}
