package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.CouponRecover;
import com.hyjf.am.trade.dao.model.auto.CouponRecoverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponRecoverMapper {
    int countByExample(CouponRecoverExample example);

    int deleteByExample(CouponRecoverExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CouponRecover record);

    int insertSelective(CouponRecover record);

    List<CouponRecover> selectByExample(CouponRecoverExample example);

    CouponRecover selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponRecover record, @Param("example") CouponRecoverExample example);

    int updateByExample(@Param("record") CouponRecover record, @Param("example") CouponRecoverExample example);

    int updateByPrimaryKeySelective(CouponRecover record);

    int updateByPrimaryKey(CouponRecover record);
}