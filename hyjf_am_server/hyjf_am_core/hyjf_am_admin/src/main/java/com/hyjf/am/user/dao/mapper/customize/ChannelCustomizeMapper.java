package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.ChannelCustomize;

import java.util.List;

public interface ChannelCustomizeMapper {

	/**
	 * 获取列表
	 * 
	 * @param channelCustomize
	 * @return
	 */
	List<ChannelCustomize> selectList(ChannelCustomize channelCustomize);

	/**
	 * COUNT
	 * 
	 * @param channelCustomize
	 * @return
	 */
	Integer countList(ChannelCustomize channelCustomize);
	
	/**
	 * 
	 * 根据id查询渠道名
	 * @author hsy
	 * @param userId
	 * @return
	 */
	String selectChannelNameByUserId(Integer userId);

}