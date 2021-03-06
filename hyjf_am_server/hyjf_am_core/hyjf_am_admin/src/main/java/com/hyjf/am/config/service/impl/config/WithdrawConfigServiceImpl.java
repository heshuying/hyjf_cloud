package com.hyjf.am.config.service.impl.config;

import com.hyjf.am.config.dao.mapper.auto.WithdrawRuleConfigMapper;
import com.hyjf.am.config.dao.mapper.auto.WithdrawTimeConfigMapper;
import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfig;
import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfigExample;
import com.hyjf.am.config.dao.model.auto.WithdrawTimeConfig;
import com.hyjf.am.config.dao.model.auto.WithdrawTimeConfigExample;
import com.hyjf.am.config.service.config.WithdrawConfigService;
import com.hyjf.am.resquest.admin.config.AdminWithdrawRuleConfigRequest;
import com.hyjf.am.resquest.admin.config.AdminWithdrawTimeConfigRequest;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
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

    @Resource
    protected WithdrawTimeConfigMapper withdrawTimeConfigMapper;

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
    public List<WithdrawRuleConfig> getWithdrawRuleConfigList(AdminWithdrawRuleConfigRequest request) {
        WithdrawRuleConfigExample example = new WithdrawRuleConfigExample();
        example.setLimitStart(request.getLimitStart());
        example.setLimitEnd(request.getLimitEnd());
        example.setOrderByClause("customer_type asc");
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

    /**
     * 保存提现时间配置
     * @param form
     * @return
     */
    @Override
    public Integer saveWithdrawTimeConfig(WithdrawTimeConfig form) {
        int recordNum = 0;
        if (form.getId()!=null){
            recordNum = withdrawTimeConfigMapper.updateByPrimaryKeySelective(form);
        }else {
            recordNum = withdrawTimeConfigMapper.insertSelective(form);
        }
        return recordNum;
    }

    /**
     * 提现时间配置详情
     * @param id
     * @return
     */
    @Override
    public WithdrawTimeConfig getWithdrawTimeConfigById(Integer id) {
        return withdrawTimeConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取提现时间配置总数
     * @return
     */
    @Override
    public int getWithdrawTimeConfigCount() {
        WithdrawTimeConfigExample example = new WithdrawTimeConfigExample();
        return withdrawTimeConfigMapper.countByExample(example);
    }

    /**
     * 获取提现时间配置列表
     * @param request
     * @return
     */
    @Override
    public List<WithdrawTimeConfig> getWithdrawTimeConfigList(AdminWithdrawTimeConfigRequest request) {
        WithdrawTimeConfigExample example = new WithdrawTimeConfigExample();
        example.setLimitStart(request.getLimitStart());
        example.setLimitEnd(request.getLimitEnd());
        return withdrawTimeConfigMapper.selectByExample(example);
    }


}
