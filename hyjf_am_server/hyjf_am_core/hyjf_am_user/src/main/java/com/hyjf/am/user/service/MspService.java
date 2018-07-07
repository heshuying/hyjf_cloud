package com.hyjf.am.user.service;

import java.util.List;
import java.util.Map;

import com.hyjf.am.user.dao.model.customize.MspConfigureCustomize;

public interface MspService extends BaseService {

	/**
	 * 获取列表
	 * 
	 * @return
	 */
	public List<MspConfigureCustomize> getRecordList(Map<String, Object> conditionMap,  int limitStart, int limitEnd);
	
	/**
	 * 获取记录数
	 * @param form
	 * @return
	 */
	public Integer getRecordCount(Map<String, Object> conditionMap);

	/**
	 * 获取单表
	 * 
	 * @return
	 */
	public MspConfigureCustomize getRecord(String id);

	/**
	 * 插入
	 * 
	 * @param record
	 */
	public void insertRecord(MspConfigureCustomize record);

	/**
	 * 是否重复
	 * 
	 * @return
	 */
	public int sourceIdIsExists(String record);

	/**
	 * 是否重复
	 * 
	 * @return
	 */
	public int sourceNameIsExists(MspConfigureCustomize mspConfigure);

	/**
	 * 更新
	 * 
	 * @param record
	 */
	public void updateRecord(MspConfigureCustomize mspConfigure);

	/**
	 * 删除
	 * 
	 * @param record
	 */
	public void deleteRecord(String sendCd);

}