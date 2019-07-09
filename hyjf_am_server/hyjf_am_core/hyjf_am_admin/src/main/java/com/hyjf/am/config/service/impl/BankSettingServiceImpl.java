/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.controller.admin.bank.BankSettingController;
import com.hyjf.am.config.dao.mapper.auto.JxBankConfigMapper;
import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.dao.model.auto.JxBankConfigExample;
import com.hyjf.am.config.service.BankSettingService;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dangzw
 * @version BankSettingService, v0.1 2018/7/25 0:02
 */
@Service
public class BankSettingServiceImpl implements BankSettingService {
    private static final Logger logger = LoggerFactory.getLogger(BankSettingServiceImpl.class);
    @Resource
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
        //删除标识
        criteria.andDelFlagEqualTo(0);
        //银行名称
        if(StringUtils.isNotBlank(jxBankConfig.getBankName())){
            criteria.andBankNameEqualTo(jxBankConfig.getBankName());
        }
        //银行联行号
        if(StringUtils.isNotBlank(jxBankConfig.getPayAllianceCode())){
            criteria.andPayAllianceCodeEqualTo(jxBankConfig.getPayAllianceCode());
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
        BeanUtils.copyProperties(adminRequest, jxBankConfig);
        if(adminRequest.getSortId()!=null) {
            jxBankConfig.setSortId(adminRequest.getSortId().shortValue());
        }
        int result = jxBankConfigMapper.insertSelective(jxBankConfig);
        logger.info("插入返回结果result="+result);
        if(result>0){
            logger.info("插入返回结果jxBankConfig"+ JSONObject.toJSONString(jxBankConfig));
            jxBankConfig.setBankId(jxBankConfig.getId());
            jxBankConfigMapper.updateByPrimaryKeySelective(jxBankConfig);
        }
        return result;
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
        record.setSortId(adminRequest.getSortId().shortValue());
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

    /**
     * 得到江西银行功能所有数据条数
     * @return
     */
    @Override
    public Integer getTotalCount(JxBankConfig jxBankConfig) {
        JxBankConfigExample example = new JxBankConfigExample();

        example.setOrderByClause("sort_id ASC");
        JxBankConfigExample.Criteria criteria = example.createCriteria();
        //删除标识insertBankSetting
        criteria.andDelFlagEqualTo(0);
        //银行名称
        if(StringUtils.isNotBlank(jxBankConfig.getBankName())){
            criteria.andBankNameEqualTo(jxBankConfig.getBankName());
        }
        //银行联行号
        if(StringUtils.isNotBlank(jxBankConfig.getPayAllianceCode())){
            criteria.andPayAllianceCodeEqualTo(jxBankConfig.getPayAllianceCode());
        }
        return jxBankConfigMapper.countByExample(example);
    }
}
