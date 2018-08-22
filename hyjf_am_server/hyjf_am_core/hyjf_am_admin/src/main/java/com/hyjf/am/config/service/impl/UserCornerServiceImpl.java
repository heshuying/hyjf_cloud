/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.UserCornerMapper;
import com.hyjf.am.config.dao.model.auto.UserCorner;
import com.hyjf.am.config.dao.model.auto.UserCornerExample;
import com.hyjf.am.config.service.UserCornerService;
import com.hyjf.am.vo.config.UserCornerVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserCornerServiceImpl, v0.1 2018/7/26 11:38
 */
@Service
public class UserCornerServiceImpl implements UserCornerService {
    @Autowired
    private UserCornerMapper userCornerMapper;
    /**
     * 根据设备唯一标识获取角标数据
     * @auth sunpeikai
     * @param sign 设备唯一标识
     * @return
     */
    @Override
    public UserCorner getUserCornerBySign(String sign) {
        UserCornerExample example = new UserCornerExample();
        UserCornerExample.Criteria criteria = example.createCriteria();
        criteria.andSignEqualTo(sign);
        List<UserCorner> userCornerList = userCornerMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(userCornerList)){
            return userCornerList.get(0);
        }
        return null;
    }

    /**
     * 更新用户角标数据
     * @auth sunpeikai
     * @param userCornerVO 用户角标数据
     * @return
     */
    @Override
    public Integer updateUserCorner(UserCornerVO userCornerVO) {
        UserCorner userCorner = CommonUtils.convertBean(userCornerVO,UserCorner.class);
        return userCornerMapper.updateByPrimaryKeySelective(userCorner);
    }

    /**
     * 插入一条新的用户角标数据
     * @auth sunpeikai
     * @param userCornerVO 用户角标数据
     * @return
     */
    @Override
    public Integer insertUserCorner(UserCornerVO userCornerVO) {
        UserCorner userCorner = CommonUtils.convertBean(userCornerVO,UserCorner.class);
        return userCornerMapper.insertSelective(userCorner);
    }
}
