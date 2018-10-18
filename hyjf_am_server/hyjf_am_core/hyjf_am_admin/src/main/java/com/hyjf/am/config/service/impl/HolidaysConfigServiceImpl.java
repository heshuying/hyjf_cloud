/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hyjf.am.config.dao.mapper.customize.HolidaysConfigCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import com.hyjf.am.config.service.HolidaysConfigService;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.http.HttpDeal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yaoy
 * @version HolidaysConfigServiceImpl, v0.1 2018/6/22 10:15
 */
@Service
public class HolidaysConfigServiceImpl implements HolidaysConfigService {
	private Logger logger = LoggerFactory.getLogger(HolidaysConfigServiceImpl.class);

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
			List<HolidaysConfig> list = this.holidaysConfigCustomizeMapper.selectByYearMonth(currentYear, index);
			List<HolidaysConfig> updateList = new ArrayList<>(list.size());
			// {"201801":{"01":"2","06":"2","07":"2","13":"1","14":"2","20":"2","21":"1","27":"2","28":"1"}}
			String result = HttpDeal.get(url + date);
			logger.info("{}月份节假日查询结果：{}", month, result);
			if (StringUtils.isEmpty(result)) {
				logger.error("{}月份节假日查询失败....", month);
				continue;
			}
			Map<String, Object> map = this.json2map(result);
			if(map!=null) {
				for (String ymStr : map.keySet()) {
					Map<String, Object> detailMap = (Map<String, Object>) map.get(ymStr);
					for (String dayStr : detailMap.keySet()) {
						int holidaysFlag = Integer.parseInt((String) detailMap.get(dayStr));
						for (HolidaysConfig holidaysConfig : list) {
							String apiQueryStr = date.concat("-").concat(dayStr);
							String sdfStr = sdf.format(holidaysConfig.getDayTime());
							if (apiQueryStr.equals(sdfStr)) {
								holidaysConfig.setHolidayFlag(holidaysFlag);
								updateList.add(holidaysConfig);
							}
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
		List<HolidaysConfig> list = new ArrayList<>(366);
		HolidaysConfig holidaysConfig = null;
		Calendar c = Calendar.getInstance();
		for (int month = 0; month < 12; month++) {
			c.set(year, month, 1);
			int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			for (int day = 1; day <= lastDay; day++) {
				holidaysConfig = new HolidaysConfig();
				c.set(year, month, day);
				holidaysConfig.setDayTime(c.getTime());
				list.add(holidaysConfig);
			}
		}
		// 先删除在插入， 防止重复
		holidaysConfigCustomizeMapper.deleteByYear(year);
		holidaysConfigCustomizeMapper.batchInsert(list);
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
