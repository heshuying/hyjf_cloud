package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.customize.FeerateModifyLogCustomizeMapper;
import com.hyjf.am.config.service.OperationLogService;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<FeerateModifyLogVO> selectOperationLogListByPage(Map<String, Object> map, int limitStart, int limitEnd){
        if (limitStart == 0 || limitStart > 0) {
            map.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            map.put("limitEnd", limitEnd);
        }
        List<FeerateModifyLogVO> list = feerateModifyLogCustomizeMapper.selectOperationLogListByPage(map);
        return list;
    }
}
