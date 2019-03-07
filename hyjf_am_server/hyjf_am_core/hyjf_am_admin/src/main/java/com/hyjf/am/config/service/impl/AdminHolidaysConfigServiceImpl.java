/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.HolidaysConfigMapper;
import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import com.hyjf.am.config.dao.model.auto.HolidaysConfigExample;
import com.hyjf.am.config.service.AdminHolidaysConfigService;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;
import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author dangzw
 * @version AdminHolidaysConfigServiceImpl, v0.1 2018/11/13 17:08
 */
@Service
public class AdminHolidaysConfigServiceImpl implements AdminHolidaysConfigService {

    private static final Logger logger = LoggerFactory.getLogger(AdminHolidaysConfigServiceImpl.class);

    @Autowired
    private HolidaysConfigMapper holidaysConfigMapper;

    /**
     * 节假日配置-列表查询
     * @param holidaysConfig
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<HolidaysConfig> getRecordList(HolidaysConfig holidaysConfig, int limitStart, int limitEnd) {
        HolidaysConfigExample example=new HolidaysConfigExample();

        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        example.createCriteria();
        return holidaysConfigMapper.selectByExample(example);
    }

    /**
     * 节假日配置-画面迁移(含有id更新，不含有id添加)
     * @param id
     * @return
     */
    @Override
    public HolidaysConfig getRecord(Integer id) {
        HolidaysConfig hConfig= holidaysConfigMapper.selectByPrimaryKey(id);
        hConfig.setStatrTime(returnDateFormat(hConfig.getStatrTime()));
        hConfig.setEndTime(returnDateFormat(hConfig.getEndTime()));
        return hConfig;
    }

    /**
     * 节假日配置-添加活动信息
     * @param request
     * @return
     */
    @Override
    public int insertHolidays(@Valid AdminHolidaysConfigRequest request) {
        HolidaysConfig holidaysConfig = new HolidaysConfig();
        request.setCreatetime(GetDate.getDate());
        request.setUpdatetime(GetDate.getDate());
        request.setStatrTime(requestDateFormat(request.getStatrTime()));
        request.setEndTime(requestDateFormat(request.getEndTime()));
        request.setYear(request.getStatrTime().substring(0,4));
        BeanUtils.copyProperties(request, holidaysConfig);
        return holidaysConfigMapper.insertSelective(holidaysConfig);
    }

    /**
     * 节假日配置-修改活动维护信息
     * @param request
     * @return
     */
    @Override
    public int updateHolidays(@Valid AdminHolidaysConfigRequest request) {
        HolidaysConfig holidaysConfig = new HolidaysConfig();
        request.setUpdatetime(GetDate.getDate());
        request.setStatrTime(requestDateFormat(request.getStatrTime()));
        request.setEndTime(requestDateFormat(request.getEndTime()));
        request.setYear(request.getStatrTime().substring(0,4));
        BeanUtils.copyProperties(request, holidaysConfig);
        return holidaysConfigMapper.updateByPrimaryKeySelective(holidaysConfig);
    }

    /**
     * 节假日配置-分页获得所有数据条数
     * @return
     */
    @Override
    public Integer getTotalCount() {
        return holidaysConfigMapper.countByExample(new HolidaysConfigExample());
    }

    /**
     * 节假日配置-请求时间格式化
     *
     * @param dateString
     * @return
     */
    private  String requestDateFormat(String dateString) {
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat rsim=new SimpleDateFormat("yyyy年MM月dd日");
        Date date=new Date();
        try {
            date = sim.parse(dateString);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return rsim.format(date);
    }

    /**
     * 节假日配置-返回时间格式化
     *
     * @param dateString
     * @return
     */
    private  String returnDateFormat(String dateString) {
        SimpleDateFormat rsim=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sim=new SimpleDateFormat("yyyy年MM月dd日");
        Date date=new Date();
        try {
            date = sim.parse(dateString);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return rsim.format(date);
    }
}
