/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;

import java.util.List;

/**
 * @author dangzw
 * @version BankSettingService, v0.1 2018/7/25 0:02
 */
public interface BankSettingService {

    /**
     *(条件)列表查询
     * @param jxBankConfig
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<JxBankConfig> getRecordList(JxBankConfig jxBankConfig,int limitStart,int limitEnd);

    /**
     * 画面迁移(含有id更新，不含有id添加)
     * @param adminRequest
     * @return
     */
    JxBankConfig bankSettingInfo(AdminBankSettingRequest adminRequest);

    /**
     * 添加
     * @param adminRequest
     */
    int insertBankSetting(AdminBankSettingRequest adminRequest);

    /**
     * 修改 江西银行 银行卡配置
     * @param adminRequest
     * @return
     */
    int updateBankSetting(AdminBankSettingRequest adminRequest);

    /**
     * 删除 江西银行 银行卡配置
     * @param id
     * @return
     */
    void deleteFeeConfig(Integer id);

    /**
     * 得到江西银行功能所有数据条数
     * @return
     */
    Integer getTotalCount(JxBankConfig jxBankConfig);
}
