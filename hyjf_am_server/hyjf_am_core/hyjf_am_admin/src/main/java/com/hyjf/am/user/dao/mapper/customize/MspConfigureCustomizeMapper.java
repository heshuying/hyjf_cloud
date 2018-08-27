package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.MspConfigureExample;
import com.hyjf.am.user.dao.model.customize.MspConfigureCustomize;



public interface MspConfigureCustomizeMapper {
    int countByExample(MspConfigureExample example);

    int deleteByExample(MspConfigureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MspConfigureCustomize record);

    int insertSelective(MspConfigureCustomize record);

    List<MspConfigureCustomize> selectByExample(MspConfigureExample example);
    
    List<MspConfigureCustomize> sourceNameIsExists(MspConfigureCustomize mspConfigure);

    MspConfigureCustomize selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MspConfigureCustomize record, @Param("example") MspConfigureExample example);

    int updateByExample(@Param("record") MspConfigureCustomize record, @Param("example") MspConfigureExample example);

    int updateByPrimaryKeySelective(MspConfigureCustomize record);

    int updateByPrimaryKey(MspConfigureCustomize record);
    //自定义开始
    /**
	 * COUNT
	 * 
	 * @param map
	 * @return
	 */
	Integer countAssetList(Map<String, Object> conditionMap);
	/**
	 * 获取列表
	 * 
	 * @param map
	 * @return
	 */
	List<MspConfigureCustomize> selectAssetListList(Map<String, Object> conditionMap);
	
	
}