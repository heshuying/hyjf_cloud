/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.trade.dao.mapper.auto.HjhInstConfigMapper;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfigExample;
import com.hyjf.am.trade.service.UserService;

/**
 * @author zhangqingqing
 * @version UserServiceImpl, v0.1 2018/5/28 12:53
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    HjhInstConfigMapper hjhInstConfigMapper;
    /**
     * 根据机构编号检索机构信息
     *
     * @param instCode
     * @return
     */
    @Override
    public HjhInstConfig selectInstConfigByInstCode(String instCode) {
        HjhInstConfigExample example = new HjhInstConfigExample();
        HjhInstConfigExample.Criteria cra = example.createCriteria();
        cra.andInstCodeEqualTo(instCode);
        List<HjhInstConfig> list = this.hjhInstConfigMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据机构编号获取机构列表
     * @param instCode
     * @return
     */
    @Override
    public  List<HjhInstConfig> selectInstConfigListByInstCode(String instCode){
        HjhInstConfigExample example = new HjhInstConfigExample();
        HjhInstConfigExample.Criteria cra = example.createCriteria();
        if(StringUtils.isNotEmpty(instCode)){
            cra.andInstCodeEqualTo(instCode);
        }
        List<HjhInstConfig> hjhInstConfigList = this.hjhInstConfigMapper.selectByExample(example);
        return  hjhInstConfigList;
    }
}
