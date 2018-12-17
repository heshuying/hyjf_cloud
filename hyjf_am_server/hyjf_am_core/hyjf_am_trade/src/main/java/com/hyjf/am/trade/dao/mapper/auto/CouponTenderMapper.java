package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.CouponTender;
import com.hyjf.am.trade.dao.model.auto.CouponTenderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponTenderMapper {
    int countByExample(CouponTenderExample example);

    int deleteByExample(CouponTenderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CouponTender record);

    int insertSelective(CouponTender record);

    List<CouponTender> selectByExample(CouponTenderExample example);

    CouponTender selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponTender record, @Param("example") CouponTenderExample example);

    int updateByExample(@Param("record") CouponTender record, @Param("example") CouponTenderExample example);

    int updateByPrimaryKeySelective(CouponTender record);

    int updateByPrimaryKey(CouponTender record);
}