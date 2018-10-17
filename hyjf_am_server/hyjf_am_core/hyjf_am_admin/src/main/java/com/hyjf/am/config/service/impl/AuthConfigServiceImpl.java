package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.*;
import com.hyjf.am.config.dao.mapper.customize.HjhUserAuthConfigCustomizeMapper;
import com.hyjf.am.config.dao.mapper.customize.HjhUserAuthConfigLogCustomizeMapper;
import com.hyjf.am.config.dao.mapper.customize.JXBankConfigCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.*;
import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigCustomize;
import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigLogCustomize;
import com.hyjf.am.config.service.AuthConfigService;
import com.hyjf.am.config.service.BankConfigService;
import com.hyjf.am.resquest.admin.AdminBankConfigRequest;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.common.util.CustomConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
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
}
