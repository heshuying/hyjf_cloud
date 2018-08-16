package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.AdminSubConfigRequest;
import com.hyjf.am.trade.dao.model.auto.SubCommissionListConfig;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/9.
 */
public interface SubConfigService {
    /*
    * 查询分账名单记录总数
    * */
    public Integer getSubConfigListCountByPage(Map<String, Object> conditionMap);

    /*
    * 分页查询分账名单记录
    * */
    public List<SubCommissionListConfig> getSubConfigListByPage(Map<String, Object> conditionMap, int limitStart, int limitEnd);

    /*
    * 根据id查询分账名单记录
    * */
    public SubCommissionListConfig getSubConfigRecordById(Integer id);
    /**
     *
     * 插入操作
     * @param form
     * @return
     */
    public int insertSubConfigRecord(AdminSubConfigRequest form);

    /**
     *
     * 更新操作
     * @return
     */
    public int updateSubConfigRecord(AdminSubConfigRequest form);

    /**
     * 删除
     */
    public void deleteSubConfigRecord(Integer id);
}
