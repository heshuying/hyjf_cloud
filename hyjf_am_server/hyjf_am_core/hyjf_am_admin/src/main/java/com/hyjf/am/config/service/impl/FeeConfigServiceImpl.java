package com.hyjf.am.config.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.config.dao.mapper.auto.FeeConfigMapper;
import com.hyjf.am.config.dao.model.auto.FeeConfig;
import com.hyjf.am.config.dao.model.auto.FeeConfigExample;
import com.hyjf.am.config.service.FeeConfigService;
import com.hyjf.am.resquest.admin.AdminFeeConfigRequest;
import com.hyjf.common.util.GetDate;

/**
 * 费率
 * create by jijun 20180616
 */
@Service
public class FeeConfigServiceImpl implements FeeConfigService {


    @Autowired
    FeeConfigMapper feeConfigMapper;


    @Override
    public List<FeeConfig> getFeeConfigs(String bankCode) {
        FeeConfigExample feeConfigExample = new FeeConfigExample();
        feeConfigExample.createCriteria().andBankCodeEqualTo(bankCode == null ? "" : bankCode);
        List<FeeConfig> listFeeConfig = feeConfigMapper.selectByExample(feeConfigExample);
        return listFeeConfig;
    }

    /**
     *  查询手续费配置列表条数
     * @return
     */
    @Override
    public Integer getFeeConfigListCount(AdminFeeConfigRequest request,int limitStart,int limitEnd){
        FeeConfigExample example = new FeeConfigExample();

        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        FeeConfigExample.Criteria criteria = example.createCriteria();
        // 条件查询
        if (request.getName() != null) {
            criteria.andNameEqualTo(request.getName());
        }
        return feeConfigMapper.countByExample(example);
    }
    /**
     *  查询手续费配置列表
     * @return
     */
    @Override
    public  List<FeeConfig> getFeeConfigListByPage(AdminFeeConfigRequest request, int limitStart, int limitEnd){
        FeeConfigExample example = new FeeConfigExample();

        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
       FeeConfigExample.Criteria criteria = example.createCriteria();
        // 条件查询
        if (request.getName() != null) {
            criteria.andNameEqualTo(request.getName());
        }
        return feeConfigMapper.selectByExample(example);
    }

    /*
     *根据id查询手续费配置详情
     * */
    @Override
    public FeeConfig getFeeConfigInfoById(Integer userId){
        return feeConfigMapper.selectByPrimaryKey(userId);
    }


    /**
     * 添加手续费配置
     * @param req
     */
    @Override
    public Integer insertFeeConfig(AdminFeeConfigRequest req) {
        FeeConfig feeConfig = new FeeConfig();
        BeanUtils.copyProperties(req,feeConfig);
        req.setCreateTime(GetDate.getDate());
        req.setUpdateTime(GetDate.getDate());
        return feeConfigMapper.insertSelective(feeConfig);
    }

    /**
     * 修改手续费配置
     * @param req
     */
    @Override
    public Integer updateFeeConfig( AdminFeeConfigRequest req) {
        FeeConfig record = new FeeConfig();
        BeanUtils.copyProperties(req,record);
        record.setUpdateTime(GetDate.getDate());
        return  feeConfigMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 删除手续费配置
     * @param id
     */
    @Override
    public void deleteFeeConfig( Integer id) {
        feeConfigMapper.deleteByPrimaryKey(id);
    }
}
