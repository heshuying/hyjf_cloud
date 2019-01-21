package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.CouponRepayMonitor;
import com.hyjf.am.trade.dao.model.auto.CouponRepayMonitorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponRepayMonitorMapper {
    int countByExample(CouponRepayMonitorExample example);

    int deleteByExample(CouponRepayMonitorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CouponRepayMonitor record);

    int insertSelective(CouponRepayMonitor record);

    List<CouponRepayMonitor> selectByExample(CouponRepayMonitorExample example);

    CouponRepayMonitor selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponRepayMonitor record, @Param("example") CouponRepayMonitorExample example);

    int updateByExample(@Param("record") CouponRepayMonitor record, @Param("example") CouponRepayMonitorExample example);

    int updateByPrimaryKeySelective(CouponRepayMonitor record);

    int updateByPrimaryKey(CouponRepayMonitor record);
}