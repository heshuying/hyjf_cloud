package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 批量更新状态
     * @param list
     * @throws Exception
     */
    void batchUpdate(List<HolidaysConfig> updateList);

    /**
     * 查询指定月份配置
     * @param year
     * @param month
     * @return
     */
    List<HolidaysConfig> selectByYearMonth(@Param("year") int year, @Param("month") int month);

    /**
     * 删除年度配置
     * @param year
     */
    void deleteByYear(int year);
}
