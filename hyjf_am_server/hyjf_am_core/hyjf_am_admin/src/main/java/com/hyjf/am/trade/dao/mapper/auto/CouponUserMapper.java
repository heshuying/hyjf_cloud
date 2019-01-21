package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.dao.model.auto.CouponUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponUserMapper {
    int countByExample(CouponUserExample example);

    int deleteByExample(CouponUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CouponUser record);

    int insertSelective(CouponUser record);

    List<CouponUser> selectByExample(CouponUserExample example);

    CouponUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponUser record, @Param("example") CouponUserExample example);

    int updateByExample(@Param("record") CouponUser record, @Param("example") CouponUserExample example);

    int updateByPrimaryKeySelective(CouponUser record);

    int updateByPrimaryKey(CouponUser record);
}