/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.HolidaysConfigMapper;
import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import com.hyjf.am.config.dao.model.auto.HolidaysConfigExample;
import com.hyjf.am.config.service.HolidaysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
