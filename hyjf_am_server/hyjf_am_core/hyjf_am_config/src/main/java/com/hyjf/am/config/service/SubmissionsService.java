package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.vo.config.SubmissionsCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version SubmissionsService, v0.1 2018/7/13 16:20
 */

public interface SubmissionsService {

    /**
     * 获取数据字典表的下拉列表
     *
     * @return
     */
    public List<ParamName> getParamNameList(String nameClass);

    /**
     * 根据查询条件 取得数据
     * @param paramMap
     * @return
     */
    List<SubmissionsCustomizeVO> queryRecordList(Map<String,Object> paramMap);

}