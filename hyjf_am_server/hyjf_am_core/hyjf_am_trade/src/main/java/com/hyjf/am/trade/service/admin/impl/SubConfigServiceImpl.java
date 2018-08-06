package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.AdminSubConfigRequest;
import com.hyjf.am.trade.dao.mapper.customize.admin.SubCommissionListConfigCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.SubCommissionListConfig;
import com.hyjf.am.trade.service.admin.SubConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/9.
 */
@Service
public class SubConfigServiceImpl implements SubConfigService {
    @Autowired
    private SubCommissionListConfigCustomizeMapper subCommissionListConfigCustomizeMapper;
    /*
    * 查询分账名单记录总数
    */
    @Override
    public Integer getSubConfigListCountByPage(Map<String, Object> conditionMap){
        return subCommissionListConfigCustomizeMapper.getSubConfigListCountByPage(conditionMap);
    }
    /*
    * 分页查询分账名单记录
    */
    @Override
    public List<SubCommissionListConfig> getSubConfigListByPage(Map<String, Object> conditionMap, int limitStart, int limitEnd){
        if (limitStart == 0 || limitStart > 0) {
            conditionMap.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            conditionMap.put("limitEnd", limitEnd);
        }
        return subCommissionListConfigCustomizeMapper.getSubConfigListByPage(conditionMap);
    }
    /*
      * 根据id查询分账名单记录
      * */
    @Override
    public SubCommissionListConfig getSubConfigRecordById(Integer id){
        return subCommissionListConfigCustomizeMapper.getSubConfigRecordById(id);
    }

    /**
     *
     * 插入操作
     * @param form
     * @return
     */
    @Override
    public int insertSubConfigRecord(AdminSubConfigRequest form){
        SubCommissionListConfig record = new SubCommissionListConfig();
        form.setCreateUserId(form.getUserId());
        BeanUtils.copyProperties(form, record);
        record.setCreateTime(new Date());
        return subCommissionListConfigCustomizeMapper.insertSubConfig(record);
    }
    /**
     *
     * 更新操作
     * @return
     */
    @Override
    public int updateSubConfigRecord(AdminSubConfigRequest form){
        SubCommissionListConfig record = new SubCommissionListConfig();
        form.setUpdateUserId(form.getUserId());
        BeanUtils.copyProperties(form, record);
        record.setUpdateTime(new Date());
        return subCommissionListConfigCustomizeMapper.updateSubConfigByPrimaryKeySelective(record);
    }

    /**
     * 删除
     */
    @Override
    public void deleteSubConfigRecord(Integer id){
        if (id != null &&id.intValue()>0) {
             subCommissionListConfigCustomizeMapper.deleteByPrimaryKey(id);
        }
    }

}
