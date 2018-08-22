/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.JxBankConfigMapper;
import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.dao.model.auto.JxBankConfigExample;
import com.hyjf.am.config.service.BankSettingService;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dangzw
 * @version BankSettingService, v0.1 2018/7/25 0:02
 */
@Service
public class BankSettingServiceImpl implements BankSettingService {

    @Autowired
    JxBankConfigMapper jxBankConfigMapper;

    /**
     *(条件)列表查询
     * @param jxBankConfig
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<JxBankConfig> getRecordList(JxBankConfig jxBankConfig, int limitStart, int limitEnd) {
        JxBankConfigExample example = new JxBankConfigExample();

        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        example.setOrderByClause("sort_id ASC");
        JxBankConfigExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(0);

        // 条件查询
        if (jxBankConfig.getBankName() != null) {
            if(!jxBankConfig.getBankName().isEmpty()){
                criteria.andBankNameEqualTo(jxBankConfig.getBankName());
            }

        }
        if (jxBankConfig.getPayAllianceCode() != null) {
            if(!jxBankConfig.getPayAllianceCode().isEmpty()){
                criteria.andPayAllianceCodeEqualTo(jxBankConfig.getPayAllianceCode());
            }

        }
        return jxBankConfigMapper.selectByExample(example);
    }

    /**
     * 画面迁移(含有id更新，不含有id添加)
     * @param adminRequest
     * @return
     */
    @Override
    public JxBankConfig bankSettingInfo(AdminBankSettingRequest adminRequest) {
        return jxBankConfigMapper.selectByPrimaryKey(Integer.valueOf(adminRequest.getId()));
    }

    /**
     * 添加
     * @param adminRequest
     */
    @Override
    public int insertBankSetting(AdminBankSettingRequest adminRequest) {
        JxBankConfig jxBankConfig = new JxBankConfig();
        adminRequest.setCreateTime(GetDate.getNowTime10());
        adminRequest.setUpdateTime(GetDate.getNowTime10());
        BeanUtils.copyProperties(adminRequest,jxBankConfig);
        return jxBankConfigMapper.insertSelective(jxBankConfig);
    }

    /**
     * 修改 江西银行 银行卡配置
     * @param adminRequest
     * @return
     */
    @Override
    public int updateBankSetting(AdminBankSettingRequest adminRequest) {
        JxBankConfig record = new JxBankConfig();
        BeanUtils.copyProperties(adminRequest,record);
        record.setUpdateTime(GetDate.getDate());
        return  jxBankConfigMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 删除 江西银行 银行卡配置
     * @param id
     * @return
     */
    @Override
    public void deleteFeeConfig(Integer id) {
        jxBankConfigMapper.deleteByPrimaryKey(id);
    }
}
