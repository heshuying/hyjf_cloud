package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.CouponSendFail;
import com.hyjf.am.market.dao.model.auto.CouponSendFailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponSendFailMapper {
    int countByExample(CouponSendFailExample example);

    int deleteByExample(CouponSendFailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CouponSendFail record);

    int insertSelective(CouponSendFail record);

    List<CouponSendFail> selectByExample(CouponSendFailExample example);

    CouponSendFail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponSendFail record, @Param("example") CouponSendFailExample example);

    int updateByExample(@Param("record") CouponSendFail record, @Param("example") CouponSendFailExample example);

    int updateByPrimaryKeySelective(CouponSendFail record);

    int updateByPrimaryKey(CouponSendFail record);
}