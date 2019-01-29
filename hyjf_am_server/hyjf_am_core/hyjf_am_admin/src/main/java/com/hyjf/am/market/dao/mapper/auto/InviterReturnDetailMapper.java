package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.InviterReturnDetail;
import com.hyjf.am.market.dao.model.auto.InviterReturnDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InviterReturnDetailMapper {
    int countByExample(InviterReturnDetailExample example);

    int deleteByExample(InviterReturnDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InviterReturnDetail record);

    int insertSelective(InviterReturnDetail record);

    List<InviterReturnDetail> selectByExample(InviterReturnDetailExample example);

    InviterReturnDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InviterReturnDetail record, @Param("example") InviterReturnDetailExample example);

    int updateByExample(@Param("record") InviterReturnDetail record, @Param("example") InviterReturnDetailExample example);

    int updateByPrimaryKeySelective(InviterReturnDetail record);

    int updateByPrimaryKey(InviterReturnDetail record);
}