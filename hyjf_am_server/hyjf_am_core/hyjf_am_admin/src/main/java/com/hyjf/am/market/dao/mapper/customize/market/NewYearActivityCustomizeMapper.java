package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.vo.admin.NewYearActivityVO;

import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2019/3/25.
 */
public interface NewYearActivityCustomizeMapper {


    /**
     * 查询累计年化出借金额 条数
     * @param map
     * @return
     */
    int getNewYearActivityCount(Map<String, Object> map);
    /**
     * 查询累计年化出借金额 条数
     * @param map
     * @return
     */
    List<NewYearActivityVO> getNewYearActivityList(Map<String, Object> map);
}
