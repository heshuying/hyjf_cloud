package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.AppPushManage;
import com.hyjf.am.trade.dao.model.auto.AppPushManageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppPushManageMapper {
    int countByExample(AppPushManageExample example);

    int deleteByExample(AppPushManageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppPushManage record);

    int insertSelective(AppPushManage record);

    List<AppPushManage> selectByExample(AppPushManageExample example);

    AppPushManage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppPushManage record, @Param("example") AppPushManageExample example);

    int updateByExample(@Param("record") AppPushManage record, @Param("example") AppPushManageExample example);

    int updateByPrimaryKeySelective(AppPushManage record);

    int updateByPrimaryKey(AppPushManage record);
}