package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.TenderCityCount;
import com.hyjf.am.trade.dao.model.customize.TenderSexCount;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/9/1  13:35
 */
public interface TenderCityCountCustomizeMapper {

    /**
     * 按照省份统计投资人的分布
     * @param date 上个月的最后一天
     */
    List<TenderCityCount> getTenderCityGroupBy(Date date);

    /**
     * 按照性别统计投资人的分布
     * @param date 上个月的最后一天
     */
    List<TenderSexCount>  getTenderSexGroupBy(Date date);

    /**
     *
     * @param date 上个月的最后一天
     * @param firstAge  年龄下限
     * @param endAge	年龄上限
     * @return
     */
    int getTenderAgeByRange(@Param("date")Date date, @Param("firstAge")int firstAge, @Param("endAge")int endAge);
}
