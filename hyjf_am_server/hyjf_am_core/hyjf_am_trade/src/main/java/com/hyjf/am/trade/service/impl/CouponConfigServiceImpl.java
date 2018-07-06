/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.CouponConfigMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.CouponConfigCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.dao.model.auto.CouponConfigExample;
import com.hyjf.am.trade.dao.model.customize.trade.CouponConfigCustomize;
import com.hyjf.am.trade.service.CouponConfigService;
import com.hyjf.common.util.CustomConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponConfigServiceImpl, v0.1 2018/6/19 19:30
 */
@Service
public class CouponConfigServiceImpl implements CouponConfigService {

	@Resource
	private CouponConfigMapper couponConfigMapper;
	@Resource
	CouponConfigCustomizeMapper couponConfigCustomizeMapper;

	/**
	 * 根据优惠券编号查找优惠券配置
	 * 
	 * @param couponCode
	 * @return
	 */
	@Override
	public CouponConfig selectCouponConfig(String couponCode) {
		CouponConfigExample cExample = new CouponConfigExample();
		cExample.createCriteria().andCouponCodeEqualTo(couponCode);
		List<CouponConfig> couponConfigList = couponConfigMapper.selectByExample(cExample);
		if (!CollectionUtils.isEmpty(couponConfigList)) {
            return couponConfigList.get(0);
        }
		return null;
	}

	/**
	 * 获取记录数
	 * @param mapParam
	 * @return
	 */
	@Override
	public int countRecord(Map<String, Object> mapParam) {
		return couponConfigCustomizeMapper.countCouponConfig(mapParam);
	}

	/**
	 * 获取列表
	 * @param mapParam
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	@Override
	public List<CouponConfigCustomize> getRecordList(Map<String, Object> mapParam, int limitStart, int limitEnd) {
		// 封装查询条件
		if (limitStart == 0 || limitStart > 0) {
			mapParam.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			mapParam.put("limitEnd", limitEnd);
		}
		//查询用户列表
		List<CouponConfigCustomize> recordList = couponConfigCustomizeMapper.selectRecordList(mapParam);
		return recordList;
	}

	/**
	 * 获取编辑页信息
	 * @param id
	 * @return
	 */
	@Override
	public CouponConfig getCouponConfig(int id) {
		CouponConfig couponConfig = couponConfigMapper.selectByPrimaryKey(id);
		if (couponConfig != null) {
			return couponConfig;
		}
		return null;
	}

	/**
	 * 保存编辑信息
	 * @param couponConfig
	 * @return
	 */
	@Override
	public Map<String, Object> saveCouponConfig(CouponConfig couponConfig) {
		int count = couponConfigMapper.updateByPrimaryKey(couponConfig);
		Map<String,Object> map = new HashMap<>();
		if (count > 0) {
			map.put("success",true);
		}else {
			map.put("msg", "保存失败");
		}
		return map;
	}

	/**
	 * 添加发行信息
	 * @param couponConfig
	 * @return
	 */
	@Override
	public Map<String, Object> insertAction(CouponConfig couponConfig) {
		int insert = couponConfigMapper.insert(couponConfig);
		Map<String,Object> map = new HashMap<>();
		if (insert > 0) {
			map.put("success", true);
		} else {
			map.put("msg", "添加失败");
		}
		return map;
	}

	/**
	 * 根据id删除发行信息
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> deleteCouponConfig(int id) {
		int i = couponConfigMapper.deleteByPrimaryKey(id);
		Map<String,Object> map = new HashMap<>();
		if (i > 0) {
			map.put("success", true);
		} else {
			map.put("msg", "删除失败");
		}
		return map;
	}

}
