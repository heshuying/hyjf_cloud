package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.Smscode;
import com.hyjf.am.user.dao.model.auto.SmscodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmscodeMapper {
    int countByExample(SmscodeExample example);

    int deleteByExample(SmscodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Smscode record);

    int insertSelective(Smscode record);

    List<Smscode> selectByExample(SmscodeExample example);

    Smscode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Smscode record, @Param("example") SmscodeExample example);

    int updateByExample(@Param("record") Smscode record, @Param("example") SmscodeExample example);

    int updateByPrimaryKeySelective(Smscode record);

    int updateByPrimaryKey(Smscode record);
}