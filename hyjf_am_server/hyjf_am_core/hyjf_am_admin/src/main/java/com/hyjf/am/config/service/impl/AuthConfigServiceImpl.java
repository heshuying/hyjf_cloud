package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.HjhUserAuthConfigLogMapper;
import com.hyjf.am.config.dao.mapper.auto.HjhUserAuthConfigMapper;
import com.hyjf.am.config.dao.mapper.customize.HjhUserAuthConfigCustomizeMapper;
import com.hyjf.am.config.dao.mapper.customize.HjhUserAuthConfigLogCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.HjhUserAuthConfig;
import com.hyjf.am.config.dao.model.auto.HjhUserAuthConfigExample;
import com.hyjf.am.config.dao.model.auto.HjhUserAuthConfigLog;
import com.hyjf.am.config.dao.model.auto.HjhUserAuthConfigLogExample;
import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigCustomize;
import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigLogCustomize;
import com.hyjf.am.config.service.AuthConfigService;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthConfigServiceImpl implements AuthConfigService {

    @Autowired
    protected HjhUserAuthConfigMapper hjhUserAuthConfigMapper;

    @Autowired
    protected HjhUserAuthConfigLogMapper hjhUserAuthConfigLogMapper;

    @Autowired
    protected HjhUserAuthConfigCustomizeMapper hjhUserAuthConfigCustomizeMapper;

    @Autowired
    protected HjhUserAuthConfigLogCustomizeMapper hjhUserAuthConfigLogCustomizeMapper;


    @Override
    public int getAuthConfigCount() {
        HjhUserAuthConfigExample example = new HjhUserAuthConfigExample();
        int count=hjhUserAuthConfigMapper.countByExample(example);
        return count;
    }

    @Override
    public List<HjhUserAuthConfigCustomize> getAuthConfigList() {
        return hjhUserAuthConfigCustomizeMapper.selectCustomizeAuthConfigList();
    }

    @Override
    public int getAuthConfigLogCount() {
        HjhUserAuthConfigLogExample example = new HjhUserAuthConfigLogExample();
        int count=hjhUserAuthConfigLogMapper.countByExample(example);
        return count;
    }

    @Override
    public List<HjhUserAuthConfigLogCustomize> getAuthConfigLogList(HjhUserAuthConfigLogCustomizeVO request) {
        List<HjhUserAuthConfigLogCustomize> list=hjhUserAuthConfigLogCustomizeMapper.selectCustomizeAuthConfigLogList(request);
        return list;
    }

    @Override
    public HjhUserAuthConfigCustomize getAuthConfigById(Integer id) {
        return hjhUserAuthConfigCustomizeMapper.getAuthConfigById(id);
    }

    @Override
    public Integer updateAuthConfig(HjhUserAuthConfig form) {
        int updateResult = hjhUserAuthConfigMapper.updateByPrimaryKeySelective(form);
        int updateResult1;
        if(updateResult==0){
            throw new RuntimeException("更新授权配置表失败!");
        }else{
            HjhUserAuthConfigLog log = new HjhUserAuthConfigLog();
            log.setAuthConfigId(form.getId());
            log.setAuthType(form.getAuthType());
            log.setPersonalMaxAmount(form.getPersonalMaxAmount());
            log.setEnterpriseMaxAmount(form.getEnterpriseMaxAmount());
            log.setAuthPeriod(form.getAuthPeriod());
            log.setEnabledStatus(form.getEnabledStatus());
            log.setRemark(form.getRemark());
            log.setCreateUserId(form.getUpdateUserId());
            log.setCreateTime(form.getUpdateTime());
            updateResult1=hjhUserAuthConfigLogMapper.insertSelective(log);
        }
        if (updateResult1==0){
            throw new RuntimeException("新增授权配置操作记录失败!");
        }
        return updateResult1;
    }
}
