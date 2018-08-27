package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.model.auto.SubCommissionListConfig;

/**
 * @author by xiehuili on 2018/7/9.
 */
public interface SubCommissionListConfigCustomizeMapper {

    /*
    * 查询分账名单记录总数
    */
    Integer getSubConfigListCountByPage(Map<String, Object> conditionMap);
    /*
    * 分页查询分账名单记录
    */
    List<SubCommissionListConfig> getSubConfigListByPage(Map<String, Object> conditionMap);

    /*
    * 根据id查询分账名单记录
    * */
    SubCommissionListConfig getSubConfigRecordById(Integer id);
    /**
     *
     * 编辑分账名单
     */
    int insertSubConfig(SubCommissionListConfig record);

    /**
     *
     * 根据id修改分账名单
     */
    int updateSubConfigByPrimaryKeySelective(SubCommissionListConfig record);


    /**
     *
     * 根据id删除分账名单
     */
    void deleteByPrimaryKey(Integer id);
}
