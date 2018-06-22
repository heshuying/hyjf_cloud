/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.mapper.customize.web.AssetManageCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.web.RecentPaymentListCustomize;
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
    AssetManageCustomizeMapper assetManageCustomizeMapper;

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
     * 获取所有机构列表
     * @return
     */
    @Override
    public  List<HjhInstConfig> selectInstConfigAll(){
        HjhInstConfigExample example = new HjhInstConfigExample();
        HjhInstConfigExample.Criteria cra = example.createCriteria();
        List<HjhInstConfig> hjhInstConfigList = this.hjhInstConfigMapper.selectByExample(example);
        return  hjhInstConfigList;
    }

    @Override
    public List<RecentPaymentListCustomize> selectRecentPaymentList(Map<String, Object> paraMap) {
        return assetManageCustomizeMapper.selectRecentPaymentList(paraMap);
    }
}
