package com.hyjf.am.trade.service.admin.underlinerecharge.impl;

import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.trade.dao.mapper.auto.UnderLineRechargeMapper;
import com.hyjf.am.trade.dao.model.auto.UnderLineRecharge;
import com.hyjf.am.trade.dao.model.auto.UnderLineRechargeExample;
import com.hyjf.am.trade.service.admin.underlinerecharge.UnderLineRechargeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author : huanghui
 */
@Service
public class UnderLineRechargeServiceImpl implements UnderLineRechargeService {

    @Autowired
    private UnderLineRechargeMapper underLineRechargeMapper;

    @Override
    public List<UnderLineRecharge> selectUnderLineList(UnderLineRechargeRequest request) {
        UnderLineRechargeExample example = new UnderLineRechargeExample();
        UnderLineRechargeExample.Criteria criteria = example.createCriteria();

        return underLineRechargeMapper.selectByExample(example);
    }

    /**
     * 数据写入
     * @param request
     * @return
     */
    @Override
    public int insterUnderRechargeCode(UnderLineRechargeRequest request) {
        UnderLineRecharge record = new UnderLineRecharge();
        // 启用状态
        request.setStatus(0);
        request.setCreateTime(new Date());
        request.setUpdateTime(new Date());
        BeanUtils.copyProperties(request, record);
        return underLineRechargeMapper.insertSelective(record);
    }

    /**
     * 充值类型 总条数
     * @param request
     * @return
     */
    @Override
    public Integer getUnderLineRechaegeCount(UnderLineRechargeRequest request) {
        UnderLineRechargeExample example = new UnderLineRechargeExample();
        UnderLineRechargeExample.Criteria criteria = example.createCriteria();
        // 启用状态的
        criteria.andStatusEqualTo(0);
        return underLineRechargeMapper.countByExample(example);
    }

    /**
     * 获取列表
     * @param request
     * @return
     */
    @Override
    public List<UnderLineRecharge> getUnderLineRechargeList(UnderLineRechargeRequest request) {
        UnderLineRechargeExample example = new UnderLineRechargeExample();
        UnderLineRechargeExample.Criteria criteria = example.createCriteria();

        // 启用状态的
        criteria.andStatusEqualTo(0);
        return underLineRechargeMapper.selectByExample(example);
    }

    /**
     * 通过Code 获取指定数据
     * @param request
     * @return
     */
    @Override
    public List<UnderLineRecharge> getUnderLineRechargeListByCode(UnderLineRechargeRequest request) {
        UnderLineRechargeExample example = new UnderLineRechargeExample();
        UnderLineRechargeExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(request.getCode()) && !"".equals(request.getCode())){
            criteria.andCodeEqualTo(request.getCode());
        }

        // 启用状态的
        criteria.andStatusEqualTo(0);
        example.setOrderByClause("create_time DESC");
        return underLineRechargeMapper.selectByExample(example);
    }

    /**
     * 更新数据
     * @param request
     * @return
     */
    @Override
    public Integer updateAction(UnderLineRechargeRequest request) {
        UnderLineRecharge record = new UnderLineRecharge();
        BeanUtils.copyProperties(request, record);
        record.setUpdateTime(new Date());
        return this.underLineRechargeMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @Override
    public Integer deleteById(Integer id) {
        return this.underLineRechargeMapper.deleteByPrimaryKey(id);
    }
}
