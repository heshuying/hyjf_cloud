package com.hyjf.am.config.service.impl.config;

import com.hyjf.am.config.dao.mapper.auto.WithdrawRuleConfigMapper;
import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfig;
import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfigExample;
import com.hyjf.am.config.service.config.WithdrawConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jun
 * @version WithdrawConfigServiceImpl, v0.1 2019/4/19 14:15
 */
@Service
public class WithdrawConfigServiceImpl implements WithdrawConfigService {

    @Resource
    protected WithdrawRuleConfigMapper withdrawRuleConfigMapper;

    /**
     * 提现规则配置总数
     * @return
     */
    @Override
    public int getWithdrawRuleConfigCount() {
        WithdrawRuleConfigExample example = new WithdrawRuleConfigExample();
        int total = withdrawRuleConfigMapper.countByExample(example);
        return total;
    }

    /**
     * 提现规则配置列表
     * @return
     */
    @Override
    public List<WithdrawRuleConfig> getWithdrawRuleConfigList() {
        WithdrawRuleConfigExample example = new WithdrawRuleConfigExample();
        List<WithdrawRuleConfig> recordList = withdrawRuleConfigMapper.selectByExample(example);
        return recordList;
    }

    /**
     * 修改提现规则配置
     * @param form
     * @return
     */
    @Override
    public Integer updateWithdrawRuleConfig(WithdrawRuleConfig form) {
        int updateRecord = withdrawRuleConfigMapper.updateByPrimaryKeySelective(form);
        return updateRecord;
    }

    /**
     * 提现规则配置详情
     * @param id
     * @return
     */
    @Override
    public WithdrawRuleConfig getWithdrawRuleConfigById(Integer id) {
        return withdrawRuleConfigMapper.selectByPrimaryKey(id);
    }


}
