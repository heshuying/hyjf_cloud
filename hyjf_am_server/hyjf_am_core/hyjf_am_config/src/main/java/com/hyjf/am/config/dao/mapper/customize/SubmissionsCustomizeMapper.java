package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.customize.SubmissionsWithBLOBs;
import com.hyjf.am.vo.config.SubmissionsCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version SubmissionsCustomizeMapper, v0.1 2018/7/13 17:25
 */

public interface SubmissionsCustomizeMapper {
    /**
     * 取得数据总数量
     *
     * @return
     */
    int countRecordTotal(Map<String,Object>  paramMap);

    /**
     * 根据查询条件 取得数据
     * @param paramMap
     * @return
     */
    List<SubmissionsCustomizeVO> queryRecordList(Map<String,Object>  paramMap);


    int updateByPrimaryKeySelective(SubmissionsWithBLOBs record);

}
