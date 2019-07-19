package com.hyjf.data.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyjf.data.trade.entity.SexDistributedInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/28
 * @Description: 金创获取统计数据
 */
public interface JcDataStatisticsCustomerMapper extends BaseMapper {


     BigDecimal getUserMonthInterest(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

     List<SexDistributedInfo> getSexDistributed(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

}
