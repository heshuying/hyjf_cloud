package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/17.
 */
public interface OperationLogService {

    /**
     * 查询配置中心操作日志配置
     * @param map
     * @return
     */
    public AdminOperationLogResponse selectOperationLogListByPage(Map<String, Object> map);


    /**
     * 配置中心操作日志配置-导出查询
     * @param map
     * @return
     */
    public List<FeerateModifyLogVO> selectOperationLogListExport(Map<String, Object> map, int limitStart, int limitEnd);
}
