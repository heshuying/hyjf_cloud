/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hyjf.am.config.dao.mapper.auto.HolidaysConfigNewMapper;
import com.hyjf.am.config.dao.mapper.customize.HolidaysConfigCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.HolidaysConfigNew;
import com.hyjf.am.config.dao.model.auto.HolidaysConfigNewExample;
import com.hyjf.am.config.service.HolidaysConfigNewService;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.http.HttpDeal;

/**
 * @author yaoy
 * @version HolidaysConfigServiceImpl, v0.1 2018/6/22 10:15
 */
@Service
public class HolidaysConfigNewServiceImpl implements HolidaysConfigNewService {
	private Logger logger = LoggerFactory.getLogger(HolidaysConfigNewServiceImpl.class);

	@Autowired
	private HolidaysConfigNewMapper holidaysConfigNewMapper;
	@Autowired
	private HolidaysConfigCustomizeMapper holidaysConfigCustomizeMapper;

	private String url = "http://www.easybots.cn/api/holiday.php?m=";

	/**
	 * 更新节假日配置 从第三方提供的api接口查询，更新本年度
	 *
	 * @return
	 * @throws ReturnMessageException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateHolidaysConfig(int currentYear) throws ReturnMessageException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String month = "";
		// 录入本年
		for (int index = 1; index <= 12; index++) {
			month = String.format("%02d", index);
			String date = currentYear +"-"+ month;
			List<HolidaysConfigNew> list = this.holidaysConfigCustomizeMapper.selectByYearMonth(currentYear, index);
			List<HolidaysConfigNew> updateList = new ArrayList<>(list.size());
			// {"201801":{"01":"2","06":"2","07":"2","13":"1","14":"2","20":"2","21":"1","27":"2","28":"1"}}
			String result = HttpDeal.get(url + date);
			logger.info("{}月份节假日查询结果：{}", month, result);
			if (StringUtils.isEmpty(result)) {
				logger.error("{}月份节假日查询失败....", month);
				continue;
			}
			Map<String, Object> map = new HashMap<>();
			map = this.json2map(result);
			for (String ymStr : map.keySet()) {
				Map<String, Object> detailMap = (Map<String, Object>) map.get(ymStr);
				for (String dayStr : detailMap.keySet()) {
					int holidaysFlag = Integer.parseInt((String) detailMap.get(dayStr));
					for (HolidaysConfigNew holidaysConfigNew : list) {
						String apiQueryStr = date.concat("-").concat(dayStr);
						String sdfStr = sdf.format(holidaysConfigNew.getDayTime());
						if (apiQueryStr.equals(sdfStr)) {
							holidaysConfigNew.setHolidayFlag(holidaysFlag);
							updateList.add(holidaysConfigNew);
						}
					}
				}
			}
			if (CollectionUtils.isEmpty(updateList)) {
				logger.error("{}月份节假日更新失败....", month);
				continue;
			}
			this.holidaysConfigCustomizeMapper.batchUpdate(updateList);
		}

		return true;
	}

	/**
	 * 初始化本年度配置
	 *
	 * @param year
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void initCurrentYearConfig(int year) {
		List<HolidaysConfigNew> list = new ArrayList<>(366);
		HolidaysConfigNew holidaysConfigNew = null;
		Calendar c = Calendar.getInstance();
		for (int month = 0; month < 12; month++) {
			c.set(year, month, 1);
			int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			for (int day = 1; day <= lastDay; day++) {
				holidaysConfigNew = new HolidaysConfigNew();
				c.set(year, month, day);
				holidaysConfigNew.setDayTime(c.getTime());
				list.add(holidaysConfigNew);
			}
		}
		// 先删除在插入， 防止重复
		holidaysConfigCustomizeMapper.deleteByYear(year);
		holidaysConfigCustomizeMapper.batchInsert(list);
	}


	/**
	 * 判断某天是否是工作日
	 *
	 * @param date
	 * @return
	 */
	@Override
	public boolean isWorkdateOnSomeDay(Date date) {
		HolidaysConfigNewExample example = new HolidaysConfigNewExample();
		HolidaysConfigNewExample.Criteria criteria = example.createCriteria();
		criteria.andDayTimeEqualTo(date);
		List<HolidaysConfigNew> list = holidaysConfigNewMapper.selectByExample(example);

		if (CollectionUtils.isEmpty(list)) {
			throw new RuntimeException("日历配置错误...");
		}
		HolidaysConfigNew holidaysConfigNewNew = list.get(0);
		if (holidaysConfigNewNew.getHolidayFlag() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 取从某天开始推后的第一个工作日开始时间
	 * @param somedate
	 * @return
	 */
	@Override
	public Date getFirstWorkdateAfterSomedate(Date somedate) {
		HolidaysConfigNewExample example = new HolidaysConfigNewExample();
		HolidaysConfigNewExample.Criteria criteria = example.createCriteria();
		criteria.andDayTimeGreaterThan(somedate);
		criteria.andHolidayFlagEqualTo(0);
		example.setOrderByClause("day_time asc");
		List<HolidaysConfigNew> list = holidaysConfigNewMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			throw new RuntimeException("日历配置错误...");
		}
		return list.get(0).getDayTime();
	}

    @Override
    public int selectFirstWorkdayOnMonth(int currentYear, int currentMonth) {
		int firstWorkDay = holidaysConfigCustomizeMapper.selectFirstWorkdayOnMonth(currentYear, currentMonth);
        return firstWorkDay;
    }

	@Override
	public Date getFirstWorkdateBeforeSomeDate(Date date) {
		HolidaysConfigNewExample example = new HolidaysConfigNewExample();
		HolidaysConfigNewExample.Criteria criteria = example.createCriteria();
		criteria.andDayTimeLessThan(date);
		criteria.andHolidayFlagEqualTo(0);
		example.setOrderByClause("day_time desc");
		List<HolidaysConfigNew> list = holidaysConfigNewMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			throw new RuntimeException("日历配置错误...");
		}
		return list.get(0).getDayTime();
	}

	private Map<String, Object> json2map(String str_json) {
		Map<String, Object> res = new HashMap<>();
		try {
			Gson gson = new Gson();
			res = gson.fromJson(str_json, new TypeToken<Map<String, Object>>() {
			}.getType());
		} catch (JsonSyntaxException e) {
			logger.error("数据转换异常...", e);
		}
		return res;
	}
}
