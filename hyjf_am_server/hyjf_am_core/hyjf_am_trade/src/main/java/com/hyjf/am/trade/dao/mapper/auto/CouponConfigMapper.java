package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.dao.model.auto.CouponConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponConfigMapper {
    int countByExample(CouponConfigExample example);

    int deleteByExample(CouponConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CouponConfig record);

    int insertSelective(CouponConfig record);

    List<CouponConfig> selectByExample(CouponConfigExample example);

    CouponConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponConfig record, @Param("example") CouponConfigExample example);

    int updateByExample(@Param("record") CouponConfig record, @Param("example") CouponConfigExample example);

    int updateByPrimaryKeySelective(CouponConfig record);

    int updateByPrimaryKey(CouponConfig record);
}