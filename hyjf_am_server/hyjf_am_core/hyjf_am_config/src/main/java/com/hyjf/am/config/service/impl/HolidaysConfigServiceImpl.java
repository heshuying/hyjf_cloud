/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.HolidaysConfigMapper;
import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import com.hyjf.am.config.dao.model.auto.HolidaysConfigExample;
import com.hyjf.am.config.service.HolidaysConfigService;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;
import com.hyjf.am.vo.trade.HolidaysConfigVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yaoy
 * @version HolidaysConfigServiceImpl, v0.1 2018/6/22 10:15
 */
@Service
public class HolidaysConfigServiceImpl implements HolidaysConfigService {

    @Autowired
    private HolidaysConfigMapper holidaysConfigMapper;

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
        hConfig.setStatrTime(returnDateFormat(hConfig.getStatrTime()));
        hConfig.setEndTime(returnDateFormat(hConfig.getEndTime()));
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
        record.setStatrTime(requestDateFormat(adminRequest.getStatrTime()));
        record.setEndTime(requestDateFormat(adminRequest.getEndTime()));
        record.setYear(adminRequest.getStatrTime().substring(0,4));
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
        record.setStatrTime(requestDateFormat(adminRequest.getStatrTime()));
        record.setEndTime(requestDateFormat(adminRequest.getEndTime()));
        record.setYear(adminRequest.getStatrTime().substring(0,4));
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
