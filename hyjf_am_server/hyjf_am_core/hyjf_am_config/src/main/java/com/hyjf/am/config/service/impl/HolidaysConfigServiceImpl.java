/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hyjf.am.config.dao.mapper.auto.HolidaysConfigMapper;
import com.hyjf.am.config.dao.mapper.customize.HolidaysConfigCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import com.hyjf.am.config.dao.model.auto.HolidaysConfigExample;
import com.hyjf.am.config.service.HolidaysConfigService;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;
import com.hyjf.am.vo.trade.HolidaysConfigVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.util.*;

/**
 * @author yaoy
 * @version HolidaysConfigServiceImpl, v0.1 2018/6/22 10:15
 */
@Service
public class HolidaysConfigServiceImpl implements HolidaysConfigService {

	@Autowired
	private HolidaysConfigMapper holidaysConfigMapper;
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
	public boolean updateHolidaysConfig() throws ReturnMessageException {
		int currentYear = LocalDate.now().getYear();
		// 1. 初始化年度数据
		this.initCurrentYearConfig(currentYear);



		//HolidaysConfig holidaysConfig = null;
		String month = "";
		// 录入本年
		for (int index = 1; index <= 12; index++) {
			month = String.format("%02d", index);
			String date = currentYear + month;
			List<HolidaysConfig> list = this.holidaysConfigCustomizeMapper.selectByMonth(date);
			List<HolidaysConfig> updateList = new ArrayList<>(list.size());
			//{"201211":{"03":"2","04":"2","10":"2","11":"1","17":"1","18":"2","24":"2","25":"1"}}
			String result = HttpDeal.get(url + date);
			Map<String,String> map =  this.json2map(result);
			for(String ymStr: map.keySet()){
				String detailResult = map.get(ymStr);
				Map<String,String> detailMap =  this.json2map(detailResult);
				for(String dayStr: map.keySet()){
					int holidaysFlag = Integer.parseInt(detailMap.get(dayStr));
					for(HolidaysConfig holidaysConfig:list){
						if(holidaysConfig.getDay().equals(date)){
							holidaysConfig.setHolidayFlag(holidaysFlag);
							updateList.add(holidaysConfig);
						}
					}
				}
			}
			this.holidaysConfigCustomizeMapper.batchUpdate(updateList);
		}
		return true;
	}

	public Map<String, String> json2map(String str_json) {
		Map<String, String> res = null;
		try {
			Gson gson = new Gson();
			res = gson.fromJson(str_json, new TypeToken<Map<String, String>>() {
			}.getType());
		} catch (JsonSyntaxException e) {
		}
		return res;
	}

	private void initCurrentYearConfig(int year) {
		List<HolidaysConfig> list = new ArrayList<>(366);
		HolidaysConfig holidaysConfig = null;
		Calendar c = Calendar.getInstance();
		for (int month = 0; month < 12; month++) {
			c.set(year, month, 1);
			int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			for (int day = 1; day <= lastDay; day++) {
				holidaysConfig = new HolidaysConfig();
				c.set(year, month, day);
				holidaysConfig.setDay(c.getTime());
				System.out.println(year + "-" + (month + 1) + "-" + day);
				list.add(holidaysConfig);
			}
		}
		holidaysConfigCustomizeMapper.batchInsert(list);
	}

    @Override
    public List<HolidaysConfig> selectHolidaysConfig(String orderByClause) {
        HolidaysConfigExample example=new HolidaysConfigExample();
        example.setOrderByClause("statr_time asc");
        List<HolidaysConfig> holidaysConfigList = holidaysConfigMapper.selectByExample(example);
        return holidaysConfigList;
    }

    /**
     * 分页查询节假日配置
     * @return
     */
    @Override
    public List<HolidaysConfig>  selectHolidaysConfigListByPage(HolidaysConfig holidaysConfig, int limitStart, int limitEnd){
        HolidaysConfigExample example=new HolidaysConfigExample();

        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        return holidaysConfigMapper.selectByExample(example);
    }

    /**
     * 查询节假日配置详情页面
     * @return
     */
    @Override
    public HolidaysConfig  selectHolidaysConfigInfo(Integer id){
        HolidaysConfig hConfig= holidaysConfigMapper.selectByPrimaryKey(id);
//        hConfig.setStatrTime(returnDateFormat(hConfig.getStatrTime()));
//        hConfig.setEndTime(returnDateFormat(hConfig.getEndTime()));
        return hConfig;
    }

    /**
     * 添加节假日配置详情页面
     * @return
     */
    @Override
    public Integer  insertHolidaysConfigInfo(AdminHolidaysConfigRequest adminRequest){
        HolidaysConfig record = CommonUtils.convertBean(adminRequest,HolidaysConfig.class);
        record.setCreateTime(adminRequest.getCreatetime());
        record.setUpdateTime(adminRequest.getUpdatetime());
//        record.setStatrTime(requestDateFormat(adminRequest.getStatrTime()));
//        record.setEndTime(requestDateFormat(adminRequest.getEndTime()));
//        record.setYear(adminRequest.getStatrTime().substring(0,4));
        return  holidaysConfigMapper.insertSelective(record);
    }

    /**
     * 修改节假日配置详情页面
     * @return
     */
    @Override
    public Integer  updateHolidaysConfigInfo(AdminHolidaysConfigRequest adminRequest){
        HolidaysConfig record = CommonUtils.convertBean(adminRequest,HolidaysConfig.class);
        record.setUpdateTime(adminRequest.getCreatetime());
//        record.setStatrTime(requestDateFormat(adminRequest.getStatrTime()));
//        record.setEndTime(requestDateFormat(adminRequest.getEndTime()));
//        record.setYear(adminRequest.getStatrTime().substring(0,4));
       return holidaysConfigMapper.updateByPrimaryKeySelective(record);
    }


    private  String requestDateFormat(String dateString) {
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat rsim=new SimpleDateFormat("yyyy年MM月dd日");
        Date date=new Date();
        try {
            date = sim.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rsim.format(date);
    }

    private  String returnDateFormat(String dateString) {
        SimpleDateFormat rsim=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sim=new SimpleDateFormat("yyyy年MM月dd日");
        Date date=new Date();
        try {
            date = sim.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rsim.format(date);
    }
}
