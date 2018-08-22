package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.FeerateModifyLog;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/17.
 */
public interface OperationLogService {

    /**
     *  查询操作日志配置条数
     * @return
     */
    public Integer selectOperationLogCountByPage( Map<String, Object> map);
    /**
     *  查询操作日志配置列表
     * @return
     */
    public List<FeerateModifyLog> selectOperationLogListByPage(Map<String, Object> map,int limitStart,int limitEnd);
//    /**
//     * 配置中心操作日志配置-导出查询
//     * @param map
//     * @return
//     */
//    @RequestMapping("/export")
//    public List<FeerateModifyLog> selectOperationLogListExport(Map<String, Object> map, int limitStart, int limitEnd);



}
