package com.hyjf.am.config.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.config.dao.model.auto.FeerateModifyLog;

/**
 * @author by xiehuili on 2018/7/17.
 */
public interface FeerateModifyLogCustomizeMapper {


    /**
     *  查询操作日志配置条数
     * @return
     */
    public Integer selectOperationLogCountByPage( Map<String, Object> map);

    /**
     *  查询操作日志配置列表
     * @return
     */
    public List<FeerateModifyLog> selectOperationLogListByPage( Map<String, Object> map);

}
