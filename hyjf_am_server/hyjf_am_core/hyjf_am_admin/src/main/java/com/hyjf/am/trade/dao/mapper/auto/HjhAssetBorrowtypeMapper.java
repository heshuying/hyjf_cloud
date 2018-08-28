package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtypeExample;

public interface HjhAssetBorrowtypeMapper {
    int countByExample(HjhAssetBorrowtypeExample example);

    int deleteByExample(HjhAssetBorrowtypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhAssetBorrowtype record);

    int insertSelective(HjhAssetBorrowtype record);

    List<HjhAssetBorrowtype> selectByExample(HjhAssetBorrowtypeExample example);

    HjhAssetBorrowtype selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhAssetBorrowtype record, @Param("example") HjhAssetBorrowtypeExample example);

    int updateByExample(@Param("record") HjhAssetBorrowtype record, @Param("example") HjhAssetBorrowtypeExample example);

    int updateByPrimaryKeySelective(HjhAssetBorrowtype record);

    int updateByPrimaryKey(HjhAssetBorrowtype record);
}