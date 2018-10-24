package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.CouponRealTender;
import com.hyjf.am.trade.dao.model.auto.CouponRealTenderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponRealTenderMapper {
    int countByExample(CouponRealTenderExample example);

    int deleteByExample(CouponRealTenderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CouponRealTender record);

    int insertSelective(CouponRealTender record);

    List<CouponRealTender> selectByExample(CouponRealTenderExample example);

    CouponRealTender selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponRealTender record, @Param("example") CouponRealTenderExample example);

    int updateByExample(@Param("record") CouponRealTender record, @Param("example") CouponRealTenderExample example);

    int updateByPrimaryKeySelective(CouponRealTender record);

    int updateByPrimaryKey(CouponRealTender record);
}