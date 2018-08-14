package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.MspQuerydetail;
import com.hyjf.am.user.dao.model.auto.MspQuerydetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MspQuerydetailMapper {
    int countByExample(MspQuerydetailExample example);

    int deleteByExample(MspQuerydetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspQuerydetail record);

    int insertSelective(MspQuerydetail record);

    List<MspQuerydetail> selectByExample(MspQuerydetailExample example);

    MspQuerydetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspQuerydetail record, @Param("example") MspQuerydetailExample example);

    int updateByExample(@Param("record") MspQuerydetail record, @Param("example") MspQuerydetailExample example);

    int updateByPrimaryKeySelective(MspQuerydetail record);

    int updateByPrimaryKey(MspQuerydetail record);
}