package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.customize.FeerateModifyLogCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.FeerateModifyLog;
import com.hyjf.am.config.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/17.
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    private FeerateModifyLogCustomizeMapper feerateModifyLogCustomizeMapper;


    /**
     *  查询操作日志配置条数
     * @return
     */
    @Override
    public Integer selectOperationLogCountByPage( Map<String, Object> map){
        return feerateModifyLogCustomizeMapper.selectOperationLogCountByPage(map);
    }
    /**
     *  查询操作日志配置列表
     * @return
     */
    @Override
    public List<FeerateModifyLog> selectOperationLogListByPage(Map<String, Object> map, int limitStart, int limitEnd){
        if (limitStart == 0 || limitStart > 0) {
            map.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            map.put("limitEnd", limitEnd);
        }
        List<FeerateModifyLog> list = feerateModifyLogCustomizeMapper.selectOperationLogListByPage(map);
        return list;
    }
//    /**
//     * 配置中心操作日志配置-导出查询
//     * @param map
//     * @return
//     */
//    @RequestMapping("/export")
//    public List<FeerateModifyLog> selectOperationLogListExport(Map<String, Object> conditionMap, int limitStart, int limitEnd){
//        if (limitStart == 0 || limitStart > 0) {
//            conditionMap.put("limitStart", limitStart);
//        }
//        if (limitEnd > 0) {
//            conditionMap.put("limitEnd", limitEnd);
//        }
//        List<FeerateModifyLog> list = feerateModifyLogCustomizeMapper.selectAssetListList(conditionMap);
//        return list;
//    }
}
