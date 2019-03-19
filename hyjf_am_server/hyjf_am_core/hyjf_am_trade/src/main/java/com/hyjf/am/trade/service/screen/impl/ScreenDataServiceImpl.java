package com.hyjf.am.trade.service.screen.impl;

import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.trade.dao.mapper.auto.UserOperateListMapper;
import com.hyjf.am.trade.dao.model.auto.UserOperateList;
import com.hyjf.am.trade.service.screen.ScreenDataService;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version ScreenDataServiceImpl, v0.1 2019/3/18 13:44
 */
@Service
public class ScreenDataServiceImpl implements ScreenDataService {
    @Autowired
    UserOperateListMapper userOperateListMapper;

    @Override
    public Integer addUserOperateList(ScreenDataBean screenDataBean) {
        UserOperateList userOperateList = CommonUtils.convertBean(screenDataBean, UserOperateList.class);

        return userOperateListMapper.insertSelective(userOperateList);
    }
}
