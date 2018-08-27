package com.hyjf.am.user.service.admin.anrong.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Service;

import com.hyjf.am.user.dao.model.customize.MspConfigureCustomize;
import com.hyjf.am.user.service.admin.anrong.MspService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;

@Service
public class MspServiceImpl extends BaseServiceImpl implements MspService {

	/**
	 * 获取列表列表
	 * 
	 * @return
	 */
	@Override
	public List<MspConfigureCustomize> getRecordList(Map<String, Object> conditionMap,  int limitStart, int limitEnd) {
		
		if (limitStart == 0 || limitStart > 0) {
			conditionMap.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			conditionMap.put("limitEnd", limitEnd);
		}
		List<MspConfigureCustomize> list = mspConfigureCustomizeMapper.selectAssetListList(conditionMap);
		return list;
	}
	/**
	 * 获取记录数
	 * @param form
	 * @return
	 * @author LiuBin
	 */
	@Override
	public Integer getRecordCount(Map<String, Object> conditionMap) {
		return mspConfigureCustomizeMapper.countAssetList(conditionMap);
	}
	/**
	 * 获取单个维护
	 * 
	 * @return
	 */
	@Override
	public MspConfigureCustomize getRecord(String record) {
		MspConfigureCustomize utmPlat = mspConfigureCustomizeMapper.selectByPrimaryKey(Integer.valueOf(record));
		return utmPlat;
	}

	/**
	 * 是否重复
	 * 
	 * @return
	 */
	@Override
	public int sourceIdIsExists(String record) {
		if (!GenericValidator.isInt(record) || !NumberUtils.isNumber(record) || Integer.valueOf(record) < 0 || StringUtils.isEmpty(record)) {
			return 1;
		}

//		UtmPlatExample example = new UtmPlatExample();
//		UtmPlatExample.Criteria cra = example.createCriteria();
//		cra.andSourceIdEqualTo(Integer.valueOf(record));
//		List<UtmPlat> utmPlatList = this.utmPlatMapper.selectByExample(example);
//		if (utmPlatList != null && utmPlatList.size() > 0) {
//			return 1;
//		}
		return 0;
	}

	/**
	 * 是否重复
	 * 
	 * @return
	 */
	@Override
	public int sourceNameIsExists(MspConfigureCustomize mspConfigure) {
		List<MspConfigureCustomize> utmPlatList = this.mspConfigureCustomizeMapper.sourceNameIsExists(mspConfigure);
		if (utmPlatList != null && utmPlatList.size() > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * 维护插入
	 * 
	 * @param record
	 */
	@Override
	public void insertRecord(MspConfigureCustomize mspConfigure) {
		mspConfigure.setCreateTime(new Date());
		mspConfigure.setUpdateTime(new Date());
		mspConfigureCustomizeMapper.insertSelective(mspConfigure);
	}

	/**
	 * 维护更新
	 * 
	 * @param record
	 */
	@Override
	public void updateRecord(MspConfigureCustomize mspConfigure) {
		mspConfigure.setUpdateTime(new Date());
		mspConfigureCustomizeMapper.updateByPrimaryKeySelective(mspConfigure);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param recordList
	 */
	@Override
	public void deleteRecord(String sourceId) {
		mspConfigureMapper.deleteByPrimaryKey(Integer.valueOf(sourceId));
	}
}
