package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.BankRechargeConfigMapper;
import com.hyjf.am.config.dao.model.auto.BankRechargeConfig;
import com.hyjf.am.config.dao.model.auto.BankRechargeConfigExample;
import com.hyjf.am.config.service.BankRechargeService;
import com.hyjf.am.resquest.admin.AdminBankRechargeConfigRequest;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/19.
 */
@Service
public class BankRechargeServiceImpl implements BankRechargeService {

    @Autowired
    private BankRechargeConfigMapper bankRechargeLimitConfigMapper;
    @Autowired
    private BankRechargeConfigMapper bankRechargeConfigMapper;

    /**
     * 查询查询快捷充值列表
     * @param adminRequest
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<BankRechargeConfig> selectBankRechargeByPage(AdminBankRechargeConfigRequest adminRequest, int limitStart, int limitEnd){
        BankRechargeConfigExample example = new BankRechargeConfigExample();
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        // 条件查询
        example.setOrderByClause("create_time");
        return bankRechargeLimitConfigMapper.selectByExample(example);
    }

    /**
     * 查询快捷充值情页面
     * @param id
     * @return
     */
    @Override
    public BankRechargeConfig selectBankRechargeById(Integer id){
        return bankRechargeLimitConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加快捷充值
     * @param adminRequest
     * @return
     */
    @Override
    public Integer insertBankRecharge(AdminBankRechargeConfigRequest adminRequest){
        BankRechargeConfig record= new BankRechargeConfig();
        BeanUtils.copyProperties(adminRequest,record);
        record.setCreateTime(GetDate.getDate());
        record.setUpdateTime(GetDate.getDate());
       return bankRechargeLimitConfigMapper.insertSelective(record);
    }
    /**
     * 修改快捷充值
     * @param adminRequest
     * @return
     */
    @Override
    public Integer updateBankRecharge(AdminBankRechargeConfigRequest adminRequest){
        BankRechargeConfig record= new BankRechargeConfig();
        BeanUtils.copyProperties(adminRequest,record);
        record.setUpdateTime(GetDate.getDate());
        return bankRechargeLimitConfigMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 删除快捷充值
     * @param id
     * @return
     */
    @Override
    public void deleteBankRecharge(Integer id){
        bankRechargeLimitConfigMapper.deleteByPrimaryKey(id);
    }
    /**
     * 查询查询快捷充值列表
     * @param adminRequest
     * @return
     */
    @Override
    public  List<BankRechargeConfig> selectExportRecordList(AdminBankRechargeConfigRequest adminRequest){
        BankRechargeConfigExample example = new BankRechargeConfigExample();
        // 条件查询
        example.setOrderByClause("create_time");
        return bankRechargeLimitConfigMapper.selectByExample(example);
    }
    /**
     * 检查银行卡是否重复
     * @return
     */
    @Override
    public int bankIsExists(AdminBankRechargeConfigRequest adminRequest){
        BankRechargeConfigExample example = new BankRechargeConfigExample();
        BankRechargeConfigExample.Criteria criteria = example.createCriteria();
        criteria.andBankIdEqualTo(adminRequest.getBankId());
        if (adminRequest.getBankId() != null && adminRequest.getBankId() != 0) {
            criteria.andIdNotEqualTo(adminRequest.getBankId());
        }
        List<BankRechargeConfig> config= bankRechargeConfigMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(config)&&config.get(0)!=null) {
            return 1;
        }
        return 0;
    }
    /**
     * 根据bankId查询BankRechargeConfig
     * @auth sunpeikai
     * @param bankId
     * @return
     */
    @Override
    public List<BankRechargeConfig> getBankRechargeConfigByBankId(Integer bankId) {
        BankRechargeConfigExample example = new BankRechargeConfigExample();
        BankRechargeConfigExample.Criteria criteria = example.createCriteria();
        criteria.andBankIdEqualTo(bankId);
        return bankRechargeConfigMapper.selectByExample(example);
    }
}
