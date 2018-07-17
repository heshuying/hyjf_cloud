package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.auto.HolidaysConfig;

import java.util.List;

/**
 * @author xiasq
 * @version HolidaysConfigCustomizeMapper, v0.1 2018/7/16 17:52
 */
public interface HolidaysConfigCustomizeMapper {

    /**
     * 批量插入
     * @param list
     * @throws Exception
     */
    void batchInsert(List<HolidaysConfig> list);

    List<HolidaysConfig> selectByMonth(String date);

    void batchUpdate(List<HolidaysConfig> updateList);
}
